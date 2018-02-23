package com.itis.android.lessondb.ui.authors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.general.Author;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a9 on 23.02.18.
 */

public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsViewHolder> {

    private List<Author> items = new ArrayList<>();
    private AuthorsAdapter.OnItemClickListener onItemClickListener;

    private final View.OnClickListener internalListener = (view) -> {
        if (onItemClickListener != null) {
            int position = (int) view.getTag();
            Author item = getItem(position);
            onItemClickListener.onItemClick(item);
        }
    };

    AuthorsAdapter(List<Author> items) {
        this.items.addAll(items);
    }

    @Override
    public AuthorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AuthorsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(AuthorsViewHolder holder, int position) {
        Author author = getItem(position);
        holder.bind(author);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(internalListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    final void changeDataSet(@NonNull List<Author> values) {
        items.clear();
        items.addAll(values);
        notifyDataSetChanged();
    }

    private Author getItem(int pos) {
        return items.get(pos);
    }

    void setOnItemClickListener(@Nullable AuthorsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull Author item);
    }

}
