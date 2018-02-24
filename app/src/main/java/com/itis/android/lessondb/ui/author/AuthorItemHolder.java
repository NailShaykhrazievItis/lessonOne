package com.itis.android.lessondb.ui.author;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;

import java.text.SimpleDateFormat;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AuthorItemHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView birthday;

    AuthorItemHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.item_name);
        birthday = itemView.findViewById(R.id.item_birthday);
    }

    public void bind(RealmAuthor author) {
        name.setText(author.getName());
        if (author.getBirthday() != null) {
            birthday.setText(new SimpleDateFormat("dd/MM/yyyy").format(author.getBirthday()));
        }
    }

    public void bind(RoomAuthor author) {
        name.setText(author.getName());
        if (author.getBirthday() != null) {
            birthday.setText(new SimpleDateFormat("dd/MM/yyyy").format(author.getBirthday()));
        }
    }
}
