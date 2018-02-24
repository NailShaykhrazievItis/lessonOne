package com.itis.android.lessondb.ui.fragments.lists.vid.lend_book_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.ui.fragments.lists.vid.VidItem;
import com.itis.android.lessondb.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class LendAdapter extends RecyclerView.Adapter<LendViewHolder>{

    private List<VidItem> vidItems = new ArrayList<>();

    private OnItemClickListener simpleItemClickListener;

    public LendAdapter(OnItemClickListener onItemClickListener) {
        this.simpleItemClickListener = onItemClickListener;
    }

    @Override
    public LendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vid, parent, false);
        return new LendViewHolder(view, simpleItemClickListener);
    }

    @Override
    public void onBindViewHolder(LendViewHolder holder, int position) {
        VidItem vid = vidItems.get(position);

        String bookDebtor = vid.getReaderName();
        String bookName = vid.getBookName();

        holder.tvBookDebtor.setText(bookDebtor);
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
