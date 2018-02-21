package com.itis.android.lessondb.ui.main.fragments.lists.vid_lists.lend_book_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.ui.utils.OnItemClickListener;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class LendViewHolder extends RecyclerView.ViewHolder{

    public TextView tvBookDebtor;

    public TextView tvBookName;


    public LendViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
        super(itemView);

        tvBookDebtor = itemView.findViewById(R.id.tv_reader_name);
        tvBookName = itemView.findViewById(R.id.tv_book_name);

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
