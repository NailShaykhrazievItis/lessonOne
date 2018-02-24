package com.itis.android.lessondb.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBook;

import java.text.SimpleDateFormat;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class MainItemHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView author;
    private TextView desc;
    private TextView genre;
    private TextView date;
    private TextView libName;

    MainItemHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.item_name);
        author = itemView.findViewById(R.id.item_author);
        desc = itemView.findViewById(R.id.item_desc);
        genre = itemView.findViewById(R.id.item_genre);
        date = itemView.findViewById(R.id.item_date);
        libName = itemView.findViewById(R.id.item_lib_name);
    }

    public void bind(RealmBook book) {
        name.setText(book.getTitle());
        author.setText(book.getRealmAuthor().getName());
        desc.setText(book.getDesc());
        if (book.getGenre() != null) {
            genre.setText(book.getGenre().getEnum().getGenreString());
        }
        if (book.getReleaseDate() != null) {
            date.setText(new SimpleDateFormat("dd/MM/yyyy").format(book.getReleaseDate()));
        }
        if (book.getRealmLibrary() != null) {
            libName.setText(book.getRealmLibrary().getName());
        }
    }

    public void bind(RoomBook book) {
        name.setText(book.getTitle());
        String authorName = AppDatabase.getAppDatabase()
                .getAuthorDao()
                .getAuthorById(book.getAuthorId())
                .getName();
        author.setText(authorName);
        desc.setText(book.getDesc());
        if (book.getGenre() != null) {
            genre.setText(book.getGenre().getCode());
        }
        if (book.getReleaseDate() != null) {
            date.setText(new SimpleDateFormat("dd/MM/yyyy").format(book.getReleaseDate()));
        }
        String libraryName = AppDatabase.getAppDatabase()
                .getLibraryDao()
                .getById(book.getLibraryId())
                .getName();
        libName.setText(libraryName);
    }
}
