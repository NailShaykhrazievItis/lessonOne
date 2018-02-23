package com.itis.android.lessondb.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositoryProvider;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.entity.RealmGenre;
import com.itis.android.lessondb.realm.entity.RealmPublishing;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.realm.entity.Genre;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomPublishing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AddNewActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText etName;
    private EditText etAuthor;
    private EditText etDesc;
    private EditText etReleaseDate;
    private EditText etGenre;
    private EditText etPublish;

    private boolean isRoom = false; //costyl'

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        etName = findViewById(R.id.et_name);
        etAuthor = findViewById(R.id.et_author);
        etDesc = findViewById(R.id.et_desc);
        etReleaseDate = findViewById(R.id.et_releaseDate);
        etGenre = findViewById(R.id.et_genre);
        etPublish = findViewById(R.id.et_publish);
        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);

        etGenre.setOnFocusChangeListener(this);
        etName.setOnFocusChangeListener(this);
        etAuthor.setOnFocusChangeListener(this);
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

        String _date = etReleaseDate.getText().toString().trim();

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = fmt.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String genre = etGenre.getText().toString().trim();

        String publish = etPublish.getText().toString().trim();
        if (etGenre.getError() != "" && date != null
                && etName.getError() != "" && etAuthor.getError() != "") {
            if (isRoom) {
                Genre roomGenre = getRoomGenre(genre);
                //roomFlow(name, authorName, desc, date, roomGenre, publish);
            } else {
                RealmGenre realmGenre = getRealmGenre(genre);
                realmFlow(name, authorName, desc, date, realmGenre, publish);
            }
            Toast.makeText(this, getString(R.string.add_book), Toast.LENGTH_SHORT).show();
        } else {
            if (date == null) {
                Toast.makeText(this, "Date field is empty!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Fields entered incorrectly!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void realmFlow(String name, String authorName, String desc, Date date, RealmGenre genre, String publish) {
        RealmBook book = new RealmBook();
        book.setTitle(name);
        book.setDesc(desc);
        book.setReleaseDate(date);
        book.setGenre(genre);

        RealmPublishing realmPublishing = new RealmPublishing();
        realmPublishing.setName(publish);

        RealmAuthor author = RepositoryProvider.provideAuthorRepository().getAuthorByName(authorName);
        if (author == null) {
            author = new RealmAuthor();
            author.setName(authorName);
            RepositoryProvider.provideAuthorRepository().insertAuthor(author);
        }
        RepositoryProvider.providePublishingRepository().insertPublishing(realmPublishing);
        book.setRealmAuthor(author);
        book.setRealmPublishing(realmPublishing);
        RepositoryProvider.provideBookRepository().insertBook(book);
    }

    private void roomFlow(String name, String authorName, String desc, Date date, Genre genre, String publish) {
        RoomBook book = new RoomBook();
        book.setTitle(name);
        book.setDesc(desc);
        book.setReleaseDate(date);

        //TODO
        //book.setGenre();
        RoomAuthor author = AppDatabase.getAppDatabase().getAuthorDao().getAuthorByName(authorName);

        long authorId;

        RoomPublishing publishing = new RoomPublishing();
        publishing.setName(publish);

        if (author == null) {
            author = new RoomAuthor();
            author.setName(authorName);
            authorId = AppDatabase.getAppDatabase().getAuthorDao().insertAuthor(author);
            book.setAuthorId(authorId);
        } else {
            book.setAuthorId(author.getId());
        }
        long publishId = AppDatabase.getAppDatabase().getPublishDao().insertPublish(publishing);
        book.setPublishId(publishId);
        AppDatabase.getAppDatabase().getBookDao().insertBook(book);
    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

    public RealmGenre getRealmGenre(String edtGenre) {
        RealmGenre realmGenre;
        switch (edtGenre.toUpperCase()) {
            case "FANTASY":
                realmGenre = new RealmGenre();
                realmGenre.saveEnum(Genre.FANTASY);
                return realmGenre;
            case "COMEDY":
                realmGenre = new RealmGenre();
                realmGenre.saveEnum(Genre.COMEDY);
                return realmGenre;
            case "DRAMA":
                realmGenre = new RealmGenre();
                realmGenre.saveEnum(Genre.DRAMA);
                return realmGenre;
            default:
                realmGenre = new RealmGenre();
                realmGenre.saveEnum(Genre.NUN);
                return realmGenre;
        }
    }

    public Genre getRoomGenre(String edtGenre) {
        Genre roomGenre;
        switch (edtGenre.toUpperCase()) {
            case "FANTASY":
                roomGenre = Genre.FANTASY;
                return roomGenre;
            case "COMEDY":
                roomGenre = Genre.COMEDY;
                return roomGenre;
            case "DRAMA":
                roomGenre = Genre.DRAMA;
                return roomGenre;
            default:
                roomGenre = Genre.NUN;
                return roomGenre;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.et_genre:
                if (Objects.equals(etGenre.getText().toString(), "")) {
                    etGenre.setError("");
                }
                break;
            case R.id.et_name:
                if (Objects.equals(etName.getText().toString(), "")) {
                    etName.setError("");
                }
                break;
            case R.id.et_author:
                if (Objects.equals(etAuthor.getText().toString(), "")) {
                    etAuthor.setError("");
                }
                break;
        }
    }
}
