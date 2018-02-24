package com.itis.android.lessondb.ui.author;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.ui.base.BaseAdapter;

import java.util.List;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AuthorAdapter extends BaseAdapter<RealmAuthor, AuthorItemHolder> {
    AuthorAdapter(List<RealmAuthor> items) {
        this.items = items;
    }

    @Override
    public AuthorItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AuthorItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_author, parent, false));
    }

    @Override
    public void onBindViewHolder(AuthorItemHolder holder, int position) {
        RealmAuthor author = getItem(position);
        holder.bind(author);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(internalListener);
    }

}
