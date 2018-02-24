package com.itis.android.lessondb.ui.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valera071998@gamil.com on 20.02.2018.
 */

public abstract class BaseAdapter<Entity, ItemHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ItemHolder> {
    protected List<Entity> items;
    protected OnListItemClickListener<Entity> onListItemClickListener;

    protected final View.OnClickListener internalListener = (view) -> {
        if (onListItemClickListener != null) {
            int position = (int) view.getTag();
            Entity item = getItem(position);
            onListItemClickListener.onItemClick(item);
        }
    };

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void changeDataSet(@NonNull List<Entity> values) {
        items.clear();
        items.addAll(values);
        notifyDataSetChanged();
    }

    protected Entity getItem(int pos) {
        return items.get(pos);
    }

    public void setOnListItemClickListener(@Nullable OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }
}
