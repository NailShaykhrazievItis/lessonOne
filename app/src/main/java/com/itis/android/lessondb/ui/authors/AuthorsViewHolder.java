package com.itis.android.lessondb.ui.authors;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.general.Author;

/**
 * Created by a9 on 23.02.18.
 */

public class AuthorsViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    //private TextView author;

    AuthorsViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.item_name);
        //author = itemView.findViewById(R.id.item_author);
    }

    public void bind(Author author) {
        name.setText(author.getName());
    }
}
