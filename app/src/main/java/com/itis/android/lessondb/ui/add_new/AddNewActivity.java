package com.itis.android.lessondb.ui.add_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.Genre;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.entity.RealmGenre;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AddNewActivity extends AppCompatActivity {

    private EditText etName;
    private AutoCompleteTextView etAuthor;
    private EditText etDescription;
    private Spinner spinGenre;

    private List<String> authors;

    private static String DATE_TAG = "date";

    private boolean isRoom = true; //costyl'

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        etName = findViewById(R.id.et_name);
        etAuthor = findViewById(R.id.et_author);
        etDescription = findViewById(R.id.et_desc);
        spinGenre = findViewById(R.id.spin_genre);
        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);
        findViewById(R.id.btn_date).setOnClickListener(this::showDatePicker);

        List<String> genreNames = new ArrayList<>();

        if (isRoom){
            com.itis.android.lessondb.room.entity.Genre[] genres = com.itis.android.lessondb.room.entity.Genre.values();
            for (com.itis.android.lessondb.room.entity.Genre genre: genres){
                genreNames.add(genre.name());
            }
            authors = roomGetAllAuthors();
        }
        else {
            Genre[] genres = Genre.values();
            for (Genre genre: genres){
                genreNames.add(genre.name());
            }
            authors = realmGetAllAuthors();
        }
        ArrayAdapter<?> spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genreNames);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGenre.setAdapter(spinAdapter);

        ArrayAdapter<?> actvAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, authors);
        etAuthor.setThreshold(1);
        etAuthor.setAdapter(actvAdapter);
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

    private void onButtonAddClicked(View view) {
        String name = etName.getText().toString().trim();
        String authorName = etAuthor.getText().toString().trim();
        String description = etDescription.getText().toString();

        Bundle extra = getIntent().getExtras();
        Calendar calendar = (Calendar) extra.get(DATE_TAG);
        Date date = new Date(calendar.getTimeInMillis());

        int genre = spinGenre.getSelectedItemPosition();

        if (isRoom) {
            roomFlow(name, authorName, description, date, genre);
        } else {
            realmFlow(name, authorName, description, date, genre);
        }
        startActivity(new Intent(this, MainActivity.class));
    }

    private void realmFlow(String name, String authorName, String description, Date releaseDate, int genre) {
        RealmBook book = new RealmBook();
        book.setTitle(name);
        RealmAuthor realmAuthor = new RealmAuthor();
        realmAuthor.setName(authorName);
        if (!authors.contains(authorName))
            RepositryProvider.provideAuthorRepository().insertAuthor(realmAuthor);
        book.setRealmAuthor(realmAuthor);
        book.setDesc(description);
        book.setReleaseDate(releaseDate);
        RealmGenre rGenre = new RealmGenre();
        rGenre.saveEnum(Genre.values()[genre]);
        book.setGenre(rGenre);
        RepositryProvider.provideBookRepository().insertBook(book);
    }

    private void roomFlow(String name, String authorName, String description, Date releaseDate, int genre) {
        RoomBook book = new RoomBook();
        book.setTitle(name);
        RoomAuthor author = new RoomAuthor();
        author.setName(authorName);
        long authorId;
        if (!authors.contains(authorName))
            authorId = AppDatabase.getAppDatabase().getAuthorDao().insertAuthor(author);
        else
            authorId = AppDatabase.getAppDatabase().getAuthorDao().getAuthorIdByName(authorName);
        book.setAuthorId(authorId);
        book.setDesc(description);
        book.setReleaseDate(releaseDate);
        book.setGenre(com.itis.android.lessondb.room.entity.Genre.values()[genre]);
        AppDatabase.getAppDatabase().getBookDao().insertBook(book);
    }

    private List<String> roomGetAllAuthors() {
        List<RoomAuthor> authors = AppDatabase
                .getAppDatabase()
                .getAuthorDao()
                .getAllAuthors();
        List<String> authorsNames = new ArrayList<>();
        for (RoomAuthor author: authors){
            authorsNames.add(author.getName());
        }
        return authorsNames;
    }

    private List<String> realmGetAllAuthors() {
        List<RealmAuthor> authors = RepositryProvider
                .provideAuthorRepository()
                .getAllAuthors().blockingFirst();
        List<String> authorsNames = new ArrayList<>();
        for (RealmAuthor author: authors){
            authorsNames.add(author.getName());
        }
        return authorsNames;
    }
}