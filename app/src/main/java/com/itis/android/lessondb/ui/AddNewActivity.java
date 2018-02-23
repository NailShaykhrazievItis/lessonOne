package com.itis.android.lessondb.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmGenre;
import com.itis.android.lessondb.realm.entity.RealmPublishingHouse;
import com.itis.android.lessondb.room.entity.Genre;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomPublishingHouse;
import com.itis.android.lessondb.ui.main.MainAdapter;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AddNewActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etAuthor;
    private EditText etDate;
    private EditText etDesc;
    private EditText etGenre;
    private EditText etPublishHouse;
    private Toolbar toolbar;

    private TextInputLayout tiGenre;

    private Calendar date = Calendar.getInstance();


    private boolean isRoom = false; //costyl'

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        etName = findViewById(R.id.et_name);
        etAuthor = findViewById(R.id.et_author);
        etDate = findViewById(R.id.et_date);
        etDesc = findViewById(R.id.et_desc);
        etGenre = findViewById(R.id.et_genre);
        etPublishHouse = findViewById(R.id.et_pub_house);

        tiGenre = findViewById(R.id.ti_genre);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etDate.setOnClickListener(this::onButtonSelectDateClicled);
        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    public void onButtonSelectDateClicled(View view) {
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
            etDate.setText(check(date.get(Calendar.DAY_OF_MONTH)) + "." + check(date.get(Calendar.MONTH)) + "." + check(date.get(Calendar.YEAR)));
        }
    };

    public String check(int a) {
        return a < 10 ? "0" + a : "" + a;
    }

    private void onButtonAddClicked(View view) {
        String name = etName.getText().toString().trim();
        String authorName = etAuthor.getText().toString().trim();
        String desc = etDesc.getText().toString();
        String publishHouseName = etPublishHouse.getText().toString();


        try {

            if (etName.getText().equals("") || etAuthor.equals("")) {
                Toast.makeText(getApplicationContext(), "Title or author is empty", Toast.LENGTH_SHORT).show();
            } else {
                if (isRoom) {
                    Genre genreRoom = Genre.valueOf(etGenre.getText().toString().toUpperCase().trim());
                    roomFlow(name, authorName, desc, genreRoom, date.getTime(),publishHouseName);
                } else {
                    com.itis.android.lessondb.realm.entity.Genre genre = com.itis.android.lessondb.realm.entity.Genre.valueOf(etGenre.getText().toString().toUpperCase().trim());
                    RealmGenre genreRealm = new RealmGenre();
                    genreRealm.saveEnum(genre);
                    realmFlow(name, authorName, desc, genreRealm, date.getTime(),publishHouseName);
                }
                Toast.makeText(this, getString(R.string.add_book), Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new MaterialDialog.Builder(this)
                    .title("Error")
                    .content("This genre does not exist")
                    .positiveText("OK")
                    .show();
        }

    }

    private void realmFlow(String name, String authorName, String desc, RealmGenre genre, Date date,String pubHouse) {
        RealmBook book = new RealmBook();
        book.setTitle(name);
        book.setGenre(genre);
        book.setReleaseDate(date);
        book.setDesc(desc);


        RealmAuthor realmAuthor = RepositryProvider.provideAuthorRepository().getAuthorByName(authorName);

        if(realmAuthor == null){
            realmAuthor = new RealmAuthor();
            realmAuthor.setName(authorName);
            RepositryProvider.provideAuthorRepository().insertAuthor(realmAuthor);
        }

        RealmPublishingHouse publishingHouse = RepositryProvider.providePublishingRepository().getPublishingHouseByName(pubHouse);

        if(publishingHouse ==null){
            publishingHouse = new RealmPublishingHouse();
            publishingHouse.setName(pubHouse);
            RepositryProvider.providePublishingRepository().insertPublishingHouse(publishingHouse);
        }

        book.setRealmAuthor(realmAuthor);
        book.setPublishingHouse(publishingHouse);

        RepositryProvider.provideBookRepository().insertBook(book);
    }

    private void roomFlow(String name, String authorName, String desc, Genre genre, Date date, String pubHouse) {
        RoomBook book = new RoomBook();
        book.setTitle(name);
        book.setGenre(genre);
        book.setReleaseDate(date);
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

        RoomPublishingHouse publishingHouse = AppDatabase.getAppDatabase().getPublishingHouseDao().getPublishingHouseByName(pubHouse);
        if(publishingHouse == null){
            publishingHouse = new RoomPublishingHouse();
            publishingHouse.setName(pubHouse);
            long publishingHouseId = AppDatabase.getAppDatabase().getPublishingHouseDao().insertPublishingHouse(publishingHouse);
            book.setPublishingHouseId(publishingHouseId);
        }
        else {
            book.setPublishingHouseId(publishingHouse.getId());
        }

            AppDatabase.getAppDatabase().getBookDao().insertBook(book);
        }

    }
