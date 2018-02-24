package com.itis.android.lessondb.ui.main;

import android.content.Entity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.ui.base.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class MainAdapter extends BaseAdapter<RealmBook, MainItemHolder> implements Filterable {
    private List<RealmBook> itemsCopy = new ArrayList<>();         // source items
    private List<String> stringFilterList;  // string performance of List<Entity>

    MainAdapter(List<RealmBook> items) {
        this.items = items;
        this.itemsCopy = items;
        this.stringFilterList = getStringList(items);
    }

    @Override
    public MainItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(MainItemHolder holder, int position) {
        RealmBook book = itemsCopy.get(position);
        holder.bind(book);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(internalListener);
    }

    @Override
    public int getItemCount() {
        return itemsCopy.size();
    }

    @Override
    public void changeDataSet(@NonNull List<RealmBook> values) {
        items.clear();
        items.addAll(values);
        // always contain all source items! to perform search
        if (stringFilterList == null || stringFilterList.size() <= values.size()) {
            stringFilterList = getStringList(values);
        }
        // if there is change item function it will have to be changed
        if (itemsCopy == null || itemsCopy.size() <= values.size()) {
            itemsCopy = values;
        }

        notifyDataSetChanged();
    }

    // filtering
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemsCopy = items;
                } else {
                    List<RealmBook> filteredList = new ArrayList<>();
                    for (int i = 0; i < stringFilterList.size(); i++) {
                        if (stringFilterList.get(i).contains(charString)) {
                            filteredList.add(items.get(i));
                        }
                    }
                    itemsCopy = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsCopy;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsCopy = (List<RealmBook>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private List<String> getStringList(List<RealmBook> values) {
        List<String> stringList = new ArrayList<>();
        for (RealmBook realmBook : values) {
            stringList.add(realmBook.getTitle());
        }
        return stringList;
    }
}
