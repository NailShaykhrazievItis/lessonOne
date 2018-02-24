package com.itis.android.lessondb.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAuthor;
    private TextView tvDescription;
    private TextView tvRelDate;
    private TextView tvGenre;

    private boolean isRoom = true;

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
        tvDescription = findViewById(R.id.tv_description);
        tvRelDate = findViewById(R.id.tv_reldate);
        tvGenre = findViewById(R.id.tv_genre);
    }

    private void realmFlow(long id) {
        RealmBook book = RepositryProvider.provideBookRepository().getBookById(id);
        tvName.setText(book.getTitle());
        tvAuthor.setText(book.getRealmAuthor().getName());
        tvDescription.setText(book.getDesc());

        Calendar relDate = new GregorianCalendar();
        relDate.setTime(book.getReleaseDate());
        String date = relDate.get(Calendar.DAY_OF_MONTH) + "."
                + (relDate.get(Calendar.MONTH) + 1) + "."
                + relDate.get(Calendar.YEAR);
        tvRelDate.setText(date);
        tvGenre.setText(book.getGenre().getEnum().name());
    }

    private void roomFlow(long id) {
        RoomBook book = AppDatabase.getAppDatabase().getBookDao().getBookById(id);
        RoomAuthor author = AppDatabase.getAppDatabase().getAuthorDao().getAuthorById(book.getAuthorId());
        tvName.setText(book.getTitle());
        tvAuthor.setText(author.getName());
        tvDescription.setText(book.getDesc());

        Calendar relDate = new GregorianCalendar();
        relDate.setTime(book.getReleaseDate());
        String date = relDate.get(Calendar.DAY_OF_MONTH) + "."
                + (relDate.get(Calendar.MONTH) + 1) + "."
                + relDate.get(Calendar.YEAR);
        tvRelDate.setText(date);
        tvGenre.setText(book.getGenre().name());
    }
}
