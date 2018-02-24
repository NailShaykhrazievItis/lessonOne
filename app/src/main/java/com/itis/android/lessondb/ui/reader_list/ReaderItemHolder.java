package com.itis.android.lessondb.ui.reader_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.room.entity.RoomReader;

public class ReaderItemHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView age;

    ReaderItemHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.item_name);
        age = itemView.findViewById(R.id.item_age);
    }

    public void bind(RealmReader reader) {
        name.setText(reader.getName());
        age.setText(String.valueOf(reader.getAge()));
    }

    public void bind(RoomReader reader) {
        name.setText(reader.getName());
        age.setText(String.valueOf(reader.getAge()));
    }
}