package com.itis.android.lessondb.ui.main.fragments.lists.book_lists.own_book_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.ui.main.fragments.lists.book_lists.BookItem;
import com.itis.android.lessondb.ui.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class OwnBookAdapter extends RecyclerView.Adapter<OwnBookViewHolder> {

    private List<BookItem> books = new ArrayList<>();

    private OnItemClickListener simpleItemClickListener;

    public OwnBookAdapter(OnItemClickListener onItemClickListener) {
        this.simpleItemClickListener = onItemClickListener;
    }

    @Override
    public OwnBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new OwnBookViewHolder(view, simpleItemClickListener);
    }

    @Override
    public void onBindViewHolder(OwnBookViewHolder holder, int position) {
        BookItem book = books.get(position);

        String bookName = book.getBookName();
        String authorName = book.getAuthorName();

        holder.tvName.setText(bookName);
        holder.tvAuthor.setText(authorName);
    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<BookItem> books) {
        this.books.clear();
        this.books.addAll(books);
        notifyDataSetChanged();
    }

}
