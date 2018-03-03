package com.itis.android.lessondb.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.entity.RealmGenre;
import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.Genre;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomReader;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AddNewActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etAuthor;
    private EditText etReader;
    private EditText etDesc;
    private EditText etGenre;
    private EditText etReleaseDate;

    private Toolbar toolbar;

    private TextInputLayout tiGenre;


    private Calendar date;
    private boolean isRoom = false; //costyl'

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        etName = findViewById(R.id.et_name);
        etAuthor = findViewById(R.id.et_author);
        etReader = findViewById(R.id.et_reader);
        etDesc = findViewById(R.id.et_desc);
        etGenre = findViewById(R.id.et_genre);
        etReleaseDate = findViewById(R.id.et_date);
        tiGenre = findViewById(R.id.ti_genre);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etReleaseDate.setOnClickListener(this::onButtonSelectDateClicled);
        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    public void onButtonSelectDateClicled(View view) {
        date = Calendar.getInstance();
        new DatePickerDialog(AddNewActivity.this, d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            etReleaseDate.setText(check(date.get(Calendar.DAY_OF_MONTH)) + "." + check(date.get(Calendar.MONTH)) + "." + check(date.get(Calendar.YEAR)));
        }
    };

    //converter from 9 to 09
    private String check(int a) {
        return a < 10 ? "0" + a : "" + a;
    }


    private void onButtonAddClicked(View view) {
        String name = etName.getText().toString().trim();
        String authorName = etAuthor.getText().toString().trim();
        String readerName = etReader.getText().toString();
        String desc = etDesc.getText().toString();


        if (etName.getText().equals("") || etAuthor.equals("")) {
            Toast.makeText(getApplicationContext(), "Title or author is empty", Toast.LENGTH_SHORT).show();
        } else {
        if (isRoom) {
            Genre genreRoom = Genre.valueOf(etGenre.getText().toString().toUpperCase().trim());

            roomFlow(name, authorName,readerName,desc,genreRoom,date);
        } else {
            com.itis.android.lessondb.realm.entity.Genre genre = com.itis.android.lessondb.realm.entity.Genre.valueOf(etGenre.getText().toString().toUpperCase().trim());
            RealmGenre genreRealm = new RealmGenre();
            genreRealm.saveEnum(genre);
            realmFlow(name, authorName,readerName,desc, genreRealm, date);
        }


        Toast.makeText(this, getString(R.string.add_book), Toast.LENGTH_SHORT).show();
    }
    }

    private void realmFlow(String name, String authorName, String readerName, String desc, RealmGenre genre, Calendar date) {
        RealmBook book = new RealmBook();
        book.setTitle(name);
        book.setGenre(genre);
        book.setReleaseDate(date.getTime());
        book.setDesc(desc);

        RealmAuthor realmAuthor = RepositryProvider.provideAuthorRepository().getAuthorByName(authorName);

        if(realmAuthor == null){
            realmAuthor = new RealmAuthor();
            realmAuthor.setName(authorName);
            RepositryProvider.provideAuthorRepository().insertAuthor(realmAuthor);
        }

        RealmReader realmReader = new RealmReader();
        realmReader.setName(readerName);
        RepositryProvider.provideReaderRepository().insertReader(realmReader);

        book.setRealmAuthor(realmAuthor);
        book.setRealmReader(realmReader);

        RepositryProvider.provideBookRepository().insertBook(book);
    }

    private void roomFlow(String name, String authorName,String readerName,String desc, Genre genre, Calendar date) {
        RoomBook book = new RoomBook();
        book.setTitle(name);
        book.setGenre(genre);
        if(date!=null){
            book.setReleaseDate(date.getTime());
        }
        book.setDesc(desc);

        RoomAuthor author = AppDatabase.getAppDatabase().getAuthorDao().getAuthorByName(authorName);
        if (author == null) {
            author = new RoomAuthor();
            author.setName(authorName);
            long authorId = AppDatabase.getAppDatabase().getAuthorDao().insertAuthor(author);
            book.setAuthorId(authorId);
            Toast.makeText(getApplicationContext(),"NEW AUTHOR",Toast.LENGTH_SHORT).show();
        }
        else {
            book.setAuthorId(author.getId());
            Toast.makeText(getApplicationContext(),"AUTHOR ALREADY EXISTS",Toast.LENGTH_SHORT).show();
        }

        RoomReader reader = AppDatabase.getAppDatabase().getReaderDao().getReaderByName(readerName);
        if (reader == null){
            reader = new RoomReader();
            reader.setName(readerName);
            long readerId = AppDatabase.getAppDatabase().getReaderDao().insertReader(reader);
            book.setReaderId(readerId);
        }
        else {
            book.setReaderId(reader.getId());
        }

        AppDatabase.getAppDatabase().getBookDao().insertBook(book);
    }
}
