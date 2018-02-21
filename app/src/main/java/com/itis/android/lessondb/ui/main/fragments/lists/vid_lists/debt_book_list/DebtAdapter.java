package com.itis.android.lessondb.ui.main.fragments.lists.vid_lists.debt_book_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.ui.main.fragments.lists.vid_lists.VidItem;
import com.itis.android.lessondb.ui.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class DebtAdapter extends RecyclerView.Adapter<DebtViewHolder> {

    private List<VidItem> vidItems = new ArrayList<>();

    private OnItemClickListener simpleItemClickListener;

    public DebtAdapter(OnItemClickListener onItemClickListener) {
        this.simpleItemClickListener = onItemClickListener;
    }

    @Override
    public DebtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vid, parent, false);
        return new DebtViewHolder(view, simpleItemClickListener);
    }

    @Override
    public void onBindViewHolder(DebtViewHolder holder, int position) {
        VidItem vid = vidItems.get(position);

        String bookOwner = vid.getReaderName();
        String bookName = vid.getBookName();

        holder.tvBookOwner.setText(bookOwner);
        holder.tvBookName.setText(bookName);
    }


    @Override
    public int getItemCount() {
        return vidItems.size();
    }

    public void setVidItems(List<VidItem> vidItems) {
        this.vidItems = vidItems;
        notifyDataSetChanged();
    }


}
