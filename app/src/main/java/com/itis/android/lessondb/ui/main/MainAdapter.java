package com.itis.android.lessondb.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.general.Book;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.entity.RoomBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainItemHolder> {

    // need change RoomBook to RealmBook for work with Realm on this class
    // для смены на room bd все параметры поменять на RoomBook
    private List<RoomBook> itemsRoom = new ArrayList<>();
    private List<RealmBook> itemsRealm = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    private final View.OnClickListener internalListener = (view) -> {
        if (onItemClickListener != null) {
            int position = (int) view.getTag();
            Book item = getItem(position);
            onItemClickListener.onItemClick(item);
        }
    };

    MainAdapter(List<RealmBook> items) {this.itemsRealm.addAll(items);
    }

    @Override
    public MainItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(MainItemHolder holder, int position) {
        RealmBook book = getItem(position);
        holder.bind(book);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(internalListener);
    }

    @Override
    public int getItemCount() {
        return itemsRealm.size();
    }

    final void changeDataSetRoom(@NonNull List<RoomBook> values) {
        itemsRoom = values;
        notifyDataSetChanged();
    }

    final void changeDataSetRealm(List<RealmBook> values){
        itemsRealm = values;
        notifyDataSetChanged();
    }

    private RealmBook getItem(int pos) {
        return itemsRealm.get(pos);
    }

    void setOnItemClickListener(@Nullable OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull Book item);
    }
}
