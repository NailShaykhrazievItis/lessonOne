package com.itis.android.lessondb.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.entity.RealmGenre;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.Genre;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AddNewActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etAuthor;
    private EditText etDesc;
    private Button btnSetDate;
    private Spinner spinGenre;

    private Calendar releaseDate;

    private boolean isRoom = true; //costyl'

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        etName = findViewById(R.id.et_name);
        etAuthor = findViewById(R.id.et_author);
        etDesc = findViewById(R.id.et_desc);

        btnSetDate = findViewById(R.id.btn_set_release_date);

        spinGenre = findViewById(R.id.spin_genre);

        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);
        btnSetDate.setOnClickListener(this::showReleaseDateDialog);
    }

    private void showReleaseDateDialog(View view) {
        DialogFragment datePickerDialog = new ReleaseDatePickerFragment();
        datePickerDialog.show(getFragmentManager(), "date picker");
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void onButtonAddClicked(View view) {
        String name = etName.getText().toString().trim();
        String authorName = etAuthor.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String genre = spinGenre.getSelectedItem().toString();
        if (isRoom) {
            roomFlow(name, authorName, desc, genre);
        } else {
            realmFlow(name, authorName, desc, genre);
        }
        Toast.makeText(this, getString(R.string.add_book), Toast.LENGTH_SHORT).show();
    }

    private void realmFlow(String name, String authorName, String desc, String genre) {
        RealmBook book = new RealmBook();
        book.setTitle(name);
        book.setDesc(desc);
        book.setReleaseDate(releaseDate.getTime());
        RealmGenre realmGenre = new RealmGenre();
        realmGenre.saveEnum(com.itis.android.lessondb.realm.entity.Genre.valueOf(genre));
        book.setGenre(realmGenre);
        RealmAuthor realmAuthor = RepositryProvider.provideAuthorRepository().getAuthorByName(authorName);
        if (realmAuthor == null) {
            realmAuthor = new RealmAuthor();
            realmAuthor.setName(authorName);
            RepositryProvider.provideAuthorRepository().insertAuthor(realmAuthor);
        }
        book.setRealmAuthor(realmAuthor);
        RepositryProvider.provideBookRepository().insertBook(book);
    }

    private void roomFlow(String name, String authorName, String desc, String genre) {
        RoomBook book = new RoomBook();
        book.setTitle(name);
        book.setDesc(desc);
        book.setReleaseDate(releaseDate.getTime());
        book.setGenre(Genre.valueOf(genre));
        RoomAuthor author = AppDatabase.getAppDatabase().getAuthorDao().getAuthorByName(authorName);
        long authorId;
        if (author == null) {
            author = new RoomAuthor();
            author.setName(authorName);
            authorId = AppDatabase.getAppDatabase().getAuthorDao().insertAuthor(author);
        }
        else {
            authorId = author.getId();
        }
        book.setAuthorId(authorId);
        AppDatabase.getAppDatabase().getBookDao().insertBook(book);
    }

    public void setDate(Calendar c) {
        releaseDate = c;
    }
}
