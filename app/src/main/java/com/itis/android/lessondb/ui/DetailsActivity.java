package com.itis.android.lessondb.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.itis.android.lessondb.App;
import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAuthor;
    private TextView tvFieldOne;
    private TextView tvFieldTwo;
    private TextView tvFieldThree;

    private boolean isRoom = App.isRoom;

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
        tvFieldOne = findViewById(R.id.tv_field_one);
        tvFieldTwo = findViewById(R.id.tv_field_two);
        tvFieldThree = findViewById(R.id.tv_field_three);
    }

    private void realmFlow(long id) {
        RealmBook book = RepositryProvider.provideBookRepository().getBookById(id);
        tvName.setText(book.getTitle());
        tvAuthor.setText(book.getRealmAuthor().getName());
    }

    private void roomFlow(long id) {
        RoomBook book = AppDatabase.getAppDatabase().getBookDao().getBookById(id);
        RoomAuthor author = AppDatabase.getAppDatabase().getAuthorDao().getAuthorById(book.getAuthorId());
        tvName.setText(book.getTitle());
        tvAuthor.setText(author.getName());
    }
}
