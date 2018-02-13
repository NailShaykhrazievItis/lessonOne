package com.itis.android.lessondb.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AddNewActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etAuthor;

    private boolean isRoom = true; //costyl'

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        etName = findViewById(R.id.et_name);
        etAuthor = findViewById(R.id.et_author);
        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void onButtonAddClicked(View view) {
        String name = etName.getText().toString().trim();
        String authorName = etAuthor.getText().toString().trim();
        if (isRoom) {
            roomFlow(name, authorName);
        } else {
            realmFlow(name, authorName);
        }
        Toast.makeText(this, getString(R.string.add_book), Toast.LENGTH_SHORT).show();
    }

    private void realmFlow(String name, String authorName) {
        RealmBook book = new RealmBook();
        book.setTitle(name);
        RealmAuthor realmAuthor = new RealmAuthor();
        realmAuthor.setName(authorName);
        RepositryProvider.provideAuthorRepository().insertAuthor(realmAuthor);
        book.setRealmAuthor(realmAuthor);
        RepositryProvider.provideBookRepository().insertBook(book);
    }

    private void roomFlow(String name, String authorName) {
        RoomBook book = new RoomBook();
        book.setTitle(name);
        RoomAuthor author = new RoomAuthor();
        author.setName(authorName);
        long authorId = AppDatabase.getAppDatabase().getAuthorDao().insertAuthor(author);
        book.setAuthorId(authorId);
        AppDatabase.getAppDatabase().getBookDao().insertBook(book);
    }
}
