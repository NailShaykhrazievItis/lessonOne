package com.itis.android.lessondb.ui.borrower;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.room.entity.RoomBorrower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blaheart on 24.02.2018.
 */

public class BorrowerAdapter extends RecyclerView.Adapter<BorrowerItemHolder> {
    private List<RoomBorrower> items = new ArrayList<>();

    BorrowerAdapter(List<RoomBorrower> items) {
        this.items.addAll(items);
    }


    @Override
    public BorrowerItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BorrowerItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(BorrowerItemHolder holder, int position) {
        RoomBorrower borrower = items.get(position);
        holder.bind(borrower);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    final void changeDataSet(@NonNull List<RoomBorrower> values) {
        items.clear();
        items.addAll(values);
        notifyDataSetChanged();
    }
}
