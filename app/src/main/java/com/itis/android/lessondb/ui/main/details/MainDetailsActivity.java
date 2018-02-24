package com.itis.android.lessondb.ui.main.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositoryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomLibrary;
import com.itis.android.lessondb.ui.base.details.BaseDetailsActivity;

import java.text.SimpleDateFormat;

public class MainDetailsActivity extends BaseDetailsActivity {
    private TextView tvName;
    private TextView tvAuthor;
    private TextView tvDesc;
    private TextView tvGenre;
    private TextView tvDate;
    private TextView tvLibName;

    @Override
    protected void initFields() {
        tvName = findViewById(R.id.tv_name);
        tvAuthor = findViewById(R.id.tv_author);
        tvDesc = findViewById(R.id.tv_desc);
        tvGenre = findViewById(R.id.tv_genre);
        tvDate = findViewById(R.id.tv_date);
        tvLibName = findViewById(R.id.tv_lib_name);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main_details;
    }

    @Override
    protected void realmFlow(long id) {
        RealmBook book = RepositoryProvider.provideBookRepository().getBookById(id);
        tvName.setText(book.getTitle());
        tvAuthor.setText(book.getRealmAuthor().getName());
        tvDesc.setText(book.getDesc());
        if (book.getGenre() != null) {
            tvGenre.setText(book.getGenre().getEnum().getGenreString());
        }
        tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(book.getReleaseDate()));
        tvLibName.setText(book.getRealmLibrary().getName());
    }

    @Override
    protected void roomFlow(long id) {
        RoomBook book = AppDatabase.getAppDatabase().getBookDao().getBookById(id);
        RoomAuthor author = AppDatabase.getAppDatabase().getAuthorDao().getAuthorById(book.getAuthorId());
        RoomLibrary library = AppDatabase.getAppDatabase().getLibraryDao().getById(book.getLibraryId());
        tvName.setText(book.getTitle());
        tvAuthor.setText(author.getName());
        tvDesc.setText(book.getDesc());
        if (book.getGenre() != null) {
            tvGenre.setText(book.getGenre().getGenreString());
        }
        tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(book.getReleaseDate()));
        tvLibName.setText(library.getName());
    }
}
