package com.itis.android.lessondb.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositoryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomPublishing;

import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAuthor;
    private TextView tvDesc;
    private TextView tvReleaseDate;
    private TextView tvGenre;
    private TextView tvPublish;

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
        tvDesc = findViewById(R.id.tv_desc);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvGenre = findViewById(R.id.tv_genre);
        tvPublish = findViewById(R.id.tv_publish);
    }

    private void realmFlow(long id) {
        RealmBook book = RepositoryProvider.provideBookRepository().getBookById(id);
        tvName.setText(book.getTitle());
        tvAuthor.setText(book.getRealmAuthor().getName());
        tvPublish.setText(book.getRealmPublishing().getName());
        tvDesc.setText(book.getDesc());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy.MM.dd");
        tvReleaseDate.setText(fmt.format(book.getReleaseDate()));
        tvGenre.setText(book.getGenre().getEnum().toString());
    }

    private void roomFlow(long id) {
        RoomBook book = AppDatabase.getAppDatabase().getBookDao().getBookById(id);
        RoomAuthor author = AppDatabase.getAppDatabase().getAuthorDao().getAuthorById(book.getAuthorId());
        RoomPublishing publishing = AppDatabase.getAppDatabase().getPublishDao().getPublishById(book.getPublishId());
        tvName.setText(book.getTitle());
        tvAuthor.setText(author.getName());
        tvPublish.setText(publishing.getName());
        tvDesc.setText(book.getDesc());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy.MM.dd");
        tvReleaseDate.setText(fmt.format(book.getReleaseDate()));
        //TODO genre
    }
}
