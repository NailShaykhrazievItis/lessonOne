package com.itis.android.lessondb.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.general.Book;
import com.itis.android.lessondb.realm.entity.RealmBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainItemHolder> implements Filterable {

    // need change RoomBook to RealmBook for work with Realm on this class
    private List<RealmBook> itemsListFiltered;
    private List<RealmBook> itemsList;
    private OnItemClickListener onItemClickListener;

    private final View.OnClickListener internalListener = (view) -> {
        if (onItemClickListener != null) {
            int position = (int) view.getTag();
            Book item = getItem(position);
            onItemClickListener.onItemClick(item);
        }
    };

    MainAdapter(List<RealmBook> items) {
        this.itemsListFiltered = items;
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
        return itemsListFiltered.size();
    }

    final void changeDataSet(@NonNull List<RealmBook> values) {
        itemsListFiltered = values;
        itemsList = values;
        notifyDataSetChanged();
    }

    private RealmBook getItem(int pos) {
        return itemsListFiltered.get(pos);
    }

    void setOnItemClickListener(@Nullable OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemsListFiltered = itemsList;
                } else {
                    List<RealmBook> filteredList = new ArrayList<>();
                    for (RealmBook row : itemsList) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getRealmAuthor().getName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    itemsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsListFiltered = (List<RealmBook>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull Book item);
    }
}
