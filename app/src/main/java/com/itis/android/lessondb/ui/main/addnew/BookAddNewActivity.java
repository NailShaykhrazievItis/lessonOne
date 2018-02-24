package com.itis.android.lessondb.ui.main.addnew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.itis.android.lessondb.App;
import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositoryProvider;
import com.itis.android.lessondb.realm.entity.Genre;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.entity.RealmGenre;
import com.itis.android.lessondb.realm.entity.RealmLibrary;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.converters.GenreConverter;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomLibrary;
import com.itis.android.lessondb.ui.base.addnew.DateAddNewActivity;
import com.itis.android.lessondb.ui.base.datepicker.DatePickerFragment;
import com.itis.android.lessondb.ui.base.datepicker.DatePickerResolver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmList;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class BookAddNewActivity extends DateAddNewActivity {

    private EditText etName;
    private EditText etAuthor;
    private EditText etDescription;
    private EditText etLibName;
    private Spinner spGenre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        etName = findViewById(R.id.et_name);
        etAuthor = findViewById(R.id.et_author);
        etDescription = findViewById(R.id.et_desc);
        etLibName = findViewById(R.id.et_lib_name);

        spGenre = findViewById(R.id.sp_genre);
        ArrayAdapter<String> spGenreAdapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        Genre.getValuesAsArray());
        spGenreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenre.setAdapter(spGenreAdapter);

        btnDate = findViewById(R.id.btn_date_picker);
        btnDate.setOnClickListener(this::onButtonAddClicked);

        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);
    }

    @Override
    protected void onButtonAddClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                String name = etName.getText().toString().trim();
                String authorName = etAuthor.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String genre = spGenre.getSelectedItem().toString().trim();
                String libName = etLibName.getText().toString().trim();

                if (isRoom) {
                    roomFlow(name, description, date, authorName, genre, libName);
                } else {
                    realmFlow(name, description, date, authorName, genre, libName);
                }

                this.finish();
                break;
            case R.id.btn_date_picker:
                datePickAction();
        }
    }

    private void realmFlow(String title, String desc, Date date, String authorName, String genre, String libName) {
        RealmBook book = new RealmBook();
        book.setTitle(title);
        book.setDesc(desc);
        book.setReleaseDate(date);
        RealmGenre realmGenre = new RealmGenre();
        realmGenre.saveEnum(Genre.getGenreByString(genre));
        book.setGenre(realmGenre);

        // check weather author exists in db
        RealmAuthor realmAuthor = RepositoryProvider.provideAuthorRepository().getAuthorByName(authorName);
        if (realmAuthor == null) {
            realmAuthor = new RealmAuthor();
            realmAuthor.setName(authorName);
            realmAuthor.getBookRealmList().add(book);
            RepositoryProvider.provideAuthorRepository().insert(realmAuthor);
        };
        book.setRealmAuthor(realmAuthor);

        // check whether library exists in db
        RealmLibrary realmLibrary = RepositoryProvider.provideLibraryRepository().getLibraryByName(libName);
        if (realmLibrary == null) {
            realmLibrary = new RealmLibrary();
            realmLibrary.setName(libName);
            realmLibrary.getBookRealmList().add(book);
            RepositoryProvider.provideLibraryRepository().insert(realmLibrary);
        }
        book.setRealmLibrary(realmLibrary);

        RepositoryProvider.provideBookRepository().insert(book);
    }

    private void roomFlow(String title, String desc, Date date, String authorName, String genre, String libName) {
        RoomBook book = new RoomBook();
        book.setTitle(title);
        book.setDesc(desc);
        book.setReleaseDate(date);
        book.setGenre(new GenreConverter().toEnum(genre));

        RoomAuthor roomAuthor = AppDatabase.getAppDatabase().getAuthorDao()
                .getAuthorByName(authorName);
        if (roomAuthor == null) {
            roomAuthor = new RoomAuthor();
            roomAuthor.setName(authorName);
            long authorId = AppDatabase.getAppDatabase().getAuthorDao()
                    .insertAuthor(roomAuthor);
            book.setAuthorId(authorId);   // binding
        };

        // check whether library exists in db
        RoomLibrary roomLibrary = AppDatabase.getAppDatabase().getLibraryDao()
                .getLibraryByName(libName);
        if (roomLibrary == null) {
            roomLibrary = new RoomLibrary();
            roomLibrary.setName(libName);
            long libraryId = AppDatabase.getAppDatabase().getLibraryDao()
                    .insert(roomLibrary);
            book.setLibraryId(libraryId);
        }

        AppDatabase.getAppDatabase().getBookDao().insertBook(book);
    }
}
