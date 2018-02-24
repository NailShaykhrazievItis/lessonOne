package com.itis.android.lessondb.ui.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmLibrary;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomLibrary;

import java.text.SimpleDateFormat;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class LibraryItemHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView address;

    LibraryItemHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.item_name);
        address = itemView.findViewById(R.id.item_address);
    }

    public void bind(RealmLibrary library) {
        name.setText(library.getName());
        if (library.getAddress() != null) {
            address.setText(library.getAddress());
        }
    }

    public void bind(RoomLibrary library) {
        name.setText(library.getName());
        if (library.getAddress() != null) {
            address.setText(library.getAddress());
        }
    }
}
