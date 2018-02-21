package com.itis.android.lessondb.ui.main.fragments.lists.book_lists.main_book_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBook;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class BookItemHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView author;

    BookItemHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.item_name);
        author = itemView.findViewById(R.id.item_author);
    }

    public void bind(RealmBook book) {
        name.setText(book.getTitle());
        author.setText(book.getRealmAuthor().getName());
    }

    public void bind(RoomBook book) {
        name.setText(book.getTitle());
        String name = AppDatabase.getAppDatabase()
                .getAuthorDao()
                .getAuthorById(book.getAuthorId())
                .getName();
        author.setText(name);
    }
}
