package com.itis.android.lessondb.ui.fragments.lists.book.own_book_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.utils.OnItemClickListener;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class OwnBookViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName;

    public TextView tvAuthor;


    public OwnBookViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
        super(itemView);

        tvName = itemView.findViewById(R.id.item_name);
        tvAuthor = itemView.findViewById(R.id.item_author);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null){
                    onItemClickListener.onClick(getAdapterPosition());
                }

            }
        });

    }
}
