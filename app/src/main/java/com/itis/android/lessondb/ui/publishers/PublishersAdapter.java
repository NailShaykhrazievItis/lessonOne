package com.itis.android.lessondb.ui.publishers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.general.Publisher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a9 on 23.02.18.
 */

public class PublishersAdapter extends RecyclerView.Adapter<PublishersViewHolder> {

    private List<Publisher> items = new ArrayList<>();
    private PublishersAdapter.OnItemClickListener onItemClickListener;

    private final View.OnClickListener internalListener = (view) -> {
        if (onItemClickListener != null) {
            int position = (int) view.getTag();
            Publisher item = getItem(position);
            onItemClickListener.onItemClick(item);
        }
    };

    PublishersAdapter(List<Publisher> items) {
        this.items.addAll(items);
    }

    @Override
    public PublishersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PublishersViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(PublishersViewHolder holder, int position) {
        Publisher publisher = getItem(position);
        holder.bind(publisher);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(internalListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    final void changeDataSet(@NonNull List<Publisher> values) {
        items.clear();
        items.addAll(values);
        notifyDataSetChanged();
    }

    private Publisher getItem(int pos) {
        return items.get(pos);
    }

    void setOnItemClickListener(@Nullable PublishersAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull Publisher item);
    }

}
