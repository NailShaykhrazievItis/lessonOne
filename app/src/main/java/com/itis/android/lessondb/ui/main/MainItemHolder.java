package com.itis.android.lessondb.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.lessondb.R;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class MainItemHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView author;

    public MainItemHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.item_name);
        author = itemView.findViewById(R.id.item_author);
    }

    public void bind(String field) {
        name.setText(field);
        author.setText("Second field");
    }
}
