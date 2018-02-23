package com.itis.android.lessondb.ui.publishers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.general.Publisher;

/**
 * Created by a9 on 23.02.18.
 */

public class PublishersViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    //private TextView author;

    PublishersViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.item_name);
        //author = itemView.findViewById(R.id.item_author);
    }

    public void bind(Publisher publisher) {
        name.setText(publisher.getName());
    }
}
