package com.itis.android.lessondb.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.Genre;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.entity.RealmGenre;
import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.dao.AuthorDao;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomReader;
import com.itis.android.lessondb.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReaderAddActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etAge;
    private Spinner spinBook;

    private List<Long> ids;

    private boolean isRoom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reader);
        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        spinBook = findViewById(R.id.spin_books);
        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);

        List<String> bookNames = new ArrayList<>();
        ids = new ArrayList<>();

        if (isRoom){
            List<RoomBook> books = roomGetAll();
            AuthorDao authorDao = AppDatabase.getAppDatabase().getAuthorDao();
            for(RoomBook book: books){
                RoomAuthor author = authorDao.getAuthorById(book.getAuthorId());
                String name = book.getTitle() + " " + author.getName();
                bookNames.add(name);
                ids.add(book.getId());
            }
        }
        else {
            List<RealmBook> books = realmGetAll();
            for(RealmBook book: books){
                String name = book.getTitle() + " " + book.getRealmAuthor().getName();
                bookNames.add(name);
                ids.add(book.getId());
            }
        }
        ArrayAdapter<?> spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookNames);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBook.setAdapter(spinAdapter);
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void onButtonAddClicked(View view) {
        String name = etName.getText().toString().trim();
        int age = Integer.parseInt(etAge.getText().toString());

        long bookId = ids.get(spinBook.getSelectedItemPosition());

        if (isRoom) {
            roomFlow(name, age, bookId);
        } else {
            realmFlow(name, age, bookId);
        }
        startActivity(new Intent(this, MainActivity.class));
    }

    private void realmFlow(String name, int age, long bookId) {
        RealmReader reader = new RealmReader();
        reader.setName(name);
        reader.setAge(age);
        reader.setBook(RepositryProvider.provideBookRepository().getBookById(bookId));
        RepositryProvider.provideReaderReposirory().insertReader(reader);
    }

    private void roomFlow(String name, int age, long bookId) {
        RoomReader reader = new RoomReader();
        reader.setName(name);
        reader.setAge(age);
        reader.setBookId(bookId);
        AppDatabase.getAppDatabase().getReaderDao().insertReader(reader);
    }

    private List<RealmBook> realmGetAll() {
        return RepositryProvider.provideBookRepository()
                .getAllBooks()
                .blockingFirst();
    }

    private List<RoomBook> roomGetAll() {
        return AppDatabase.getAppDatabase()
                .getBookDao()
                .getAllBooks()
                .blockingGet();
    }
}
