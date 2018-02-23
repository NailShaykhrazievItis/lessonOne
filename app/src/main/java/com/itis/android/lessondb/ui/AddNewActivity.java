package com.itis.android.lessondb.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.itis.android.lessondb.App;
import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.GenreRealm;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.entity.RealmPublisher;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.GenreRoom;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomPublisher;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AddNewActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etAuthor;

    private EditText etPublisher;
    private EditText etGenre;
    private DatePicker dpBook;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        etName = findViewById(R.id.et_name);
        etAuthor = findViewById(R.id.et_author);

        etPublisher = findViewById(R.id.et_publisher);
        dpBook = findViewById(R.id.dp_book);
        etGenre = findViewById(R.id.et_genre);

        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void onButtonAddClicked(View view) {

        //check genre
        try {
            GenreRealm g1 = GenreRealm.valueOf(etGenre.getText().toString().trim());
            GenreRoom g2 = GenreRoom.valueOf(etGenre.getText().toString().trim());
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            return;
        }

        String name = etName.getText().toString().trim();
        String authorName = etAuthor.getText().toString().trim();
        String publisherName = etPublisher.getText().toString().trim();

        int day = dpBook.getDayOfMonth();
        int month = dpBook.getMonth();
        int year = dpBook.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date bookDate = calendar.getTime();

        String genreString = etGenre.getText().toString().trim();

        if (App.isRoom) {
            roomFlow(name, authorName, publisherName, genreString, bookDate);
        } else {
            realmFlow(name, authorName, publisherName, genreString, bookDate);
        }
        Toast.makeText(this, getString(R.string.add_book), Toast.LENGTH_SHORT).show();
    }

    private void realmFlow(String name, String authorName, String publisherName, String genreString, Date bookDate) {
        RealmBook book = new RealmBook();
        book.setTitle(name);
        book.setReleaseDate(bookDate);

        RealmAuthor realmAuthor = RepositryProvider.provideAuthorRepository().getAuthorByName(authorName);
        if (realmAuthor == null) {
            realmAuthor = new RealmAuthor();
            realmAuthor.setName(authorName);
            RepositryProvider.provideAuthorRepository().insertAuthor(realmAuthor);
        }
        book.setRealmAuthor(realmAuthor);

        RealmPublisher realmPublisher = RepositryProvider.providePublisherRepository().getPublisherByName(publisherName);
        if (realmPublisher == null) {
            realmPublisher = new RealmPublisher();
            realmPublisher.setName(publisherName);
            RepositryProvider.providePublisherRepository().insertPublisher(realmPublisher);
        }
        book.setRealmPublisher(realmPublisher);

        GenreRealm genreRealm = GenreRealm.valueOf(genreString);

        book.setGenreRealm(genreRealm);

        RepositryProvider.provideBookRepository().insertBook(book);

        Log.d("Alm", "Realm book add:" + book.toString());
    }

    private void roomFlow(String name, String authorName, String publisherName, String genreString, Date bookDate) {
        RoomBook book = new RoomBook();
        book.setTitle(name);
        book.setReleaseDate(bookDate);

        GenreRoom genreRoom = GenreRoom.valueOf(genreString);
        book.setGenre(genreRoom);

        RoomAuthor author = AppDatabase.getAppDatabase().getAuthorDao().getAuthorByName(authorName);
        if (author == null) {
            author = new RoomAuthor();
            author.setName(authorName);
            long authorId = AppDatabase.getAppDatabase().getAuthorDao().insertAuthor(author);
            author.setId(authorId);
        }
        book.setAuthorId(author.getId());

        RoomPublisher publisher = AppDatabase.getAppDatabase().getPublisherDao().getPublisherByName(publisherName);
        if (publisher == null) {
            publisher = new RoomPublisher();
            publisher.setName(publisherName);
            long publisherId = AppDatabase.getAppDatabase().getPublisherDao().insertPublisher(publisher);
            publisher.setId(publisherId);
        }
        book.setPublisherId(publisher.getId());

        AppDatabase.getAppDatabase().getBookDao().insertBook(book);

        Log.d("Alm", "Room book add:" + book.toString());
        Log.d("Alm", "Room author:" + author.toString());
        Log.d("Alm", "Room publisher:" + publisher.toString());

    }
}
