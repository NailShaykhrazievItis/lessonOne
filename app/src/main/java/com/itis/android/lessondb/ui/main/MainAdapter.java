package com.itis.android.lessondb.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainItemHolder> {

    private List<String> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    private final View.OnClickListener internalListener = (view) -> {
        if (onItemClickListener != null) {
            int position = (int) view.getTag();
            String item = getItem(position);
            onItemClickListener.onItemClick(item);
        }
    };

    public MainAdapter(List<String> items) {
        this.items.addAll(items);
    }

    @Override
    public MainItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(MainItemHolder holder, int position) {
        String name = getItem(position);
        holder.bind(name);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(internalListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final void changeDataSet(@NonNull List<String> values) {
        items.clear();
        items.addAll(values);
        notifyDataSetChanged();
    }

    private String getItem(int pos) {
        return items.get(pos);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull String item);
    }
}
