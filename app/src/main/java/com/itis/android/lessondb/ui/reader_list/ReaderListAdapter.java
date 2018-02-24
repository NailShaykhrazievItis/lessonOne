package com.itis.android.lessondb.ui.reader_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.room.entity.RoomReader;

import java.util.ArrayList;
import java.util.List;

public class ReaderListAdapter extends RecyclerView.Adapter<ReaderItemHolder>  {

    private List<RealmReader> readers = new ArrayList<>();

    public ReaderListAdapter(List<RealmReader> readers) {
        this.readers = readers;
    }

    @Override
    public ReaderItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReaderItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reader, parent, false));
    }

    @Override
    public void onBindViewHolder(ReaderItemHolder holder, int position) {
        RealmReader reader = getItem(position);
        holder.bind(reader);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return readers.size();
    }

    final void changeDataSet(@NonNull List<RealmReader> values) {
        readers.clear();
        readers.addAll(values);
        notifyDataSetChanged();
    }

    private RealmReader getItem(int pos) {
        return readers.get(pos);
    }
}
