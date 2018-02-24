package com.itis.android.lessondb.ui.borrower;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.entity.RealmBorrower;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBorrower;

/**
 * Created by Blaheart on 24.02.2018.
 */

public class BorrowerItemHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView bookName;

    public BorrowerItemHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.item_name);
        bookName = itemView.findViewById(R.id.item_author);
    }

    public void bind(RealmBorrower borrower) {
        name.setText(borrower.getName());
        bookName.setText(borrower.getBook().getTitle());
    }

    public void bind(RoomBorrower borrower) {
        name.setText(borrower.getName());
        String book = AppDatabase
                .getAppDatabase()
                .getBookDao()
                .getBookById(borrower.getBookId())
                .getTitle();
        bookName.setText(book);
    }
}
