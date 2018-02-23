package com.itis.android.lessondb.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomPublishingHouse;

import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAuthor;
    private TextView tvDescription;
    private TextView tvGenre;
    private TextView tvDate;
    private TextView tvPublishingHouse;
    private Toolbar toolbar;

    private boolean isRoom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initFields();
        long id = getIntent().getLongExtra("item", 0L);
        if (isRoom) {
            roomFlow(id);
        } else {
            realmFlow(id);
        }
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void initFields() {
        tvName = findViewById(R.id.tv_name);
        tvAuthor = findViewById(R.id.tv_author);
        tvDescription = findViewById(R.id.tv_desc);
        tvGenre = findViewById(R.id.tv_genre);
        tvDate = findViewById(R.id.tv_date);
        tvPublishingHouse = findViewById(R.id.tv_pub_house);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void realmFlow(long id) {
        RealmBook book = RepositryProvider.provideBookRepository().getBookById(id);
        tvName.setText(book.getTitle());
        tvAuthor.setText(book.getRealmAuthor().getName());
        tvDescription.setText(book.getDesc());
        tvGenre.setText(book.getGenre().getEnum().toString());
        tvDate.setText(new SimpleDateFormat("dd.MM.yyyy").format(book.getReleaseDate()));
        tvPublishingHouse.setText(book.getPublishingHouse().getName());
    }

    private void roomFlow(long id) {
        RoomBook book = AppDatabase.getAppDatabase().getBookDao().getBookById(id);
        RoomAuthor author = AppDatabase.getAppDatabase().getAuthorDao().getAuthorById(book.getAuthorId());
        RoomPublishingHouse roomPublishingHouse = AppDatabase.getAppDatabase().getPublishingHouseDao().getPublishingHouseById(book.getId());
        tvName.setText(book.getTitle());
        tvAuthor.setText(author.getName());
        tvDescription.setText(book.getDesc());
        tvGenre.setText(book.getGenre().toString());
        tvDate.setText(new SimpleDateFormat("dd.MM.yyyy").format(book.getReleaseDate()));
        tvPublishingHouse.setText(roomPublishingHouse.getName());
    }
}
