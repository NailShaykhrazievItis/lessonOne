package com.itis.android.lessondb.ui.library;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmLibrary;
import com.itis.android.lessondb.ui.base.BaseAdapter;

import java.util.List;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class LibraryAdapter extends BaseAdapter<RealmLibrary, LibraryItemHolder> {
    LibraryAdapter(List<RealmLibrary> items) {
        this.items = items;
    }

    @Override
    public LibraryItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LibraryItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_library, parent, false));
    }

    @Override
    public void onBindViewHolder(LibraryItemHolder holder, int position) {
        RealmLibrary library = getItem(position);
        holder.bind(library);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(internalListener);
    }

}
