package com.itis.android.lessondb.ui.main.fragments.lists.book_lists.own_book_list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomVid;
import com.itis.android.lessondb.ui.base.BaseFragment;
import com.itis.android.lessondb.ui.main.fragments.lists.book_lists.BookDetailsFragment;
import com.itis.android.lessondb.ui.main.fragments.lists.book_lists.BookItem;
import com.itis.android.lessondb.ui.main.fragments.lists.book_lists.main_book_list.BookListFragment;
import com.itis.android.lessondb.ui.main.fragments.lists.vid_lists.debt_book_list.DebtListFragment;
import com.itis.android.lessondb.ui.main.fragments.lists.vid_lists.lend_book_list.LendListFragment;
import com.itis.android.lessondb.ui.utils.Const;
import com.itis.android.lessondb.ui.utils.OnItemClickListener;
import com.itis.android.lessondb.ui.utils.SimpleSession;
import com.itis.android.lessondb.ui.utils.VidHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class OwnBookListFragment extends BaseFragment implements OnItemClickListener {

    private static final String MY_BOOK_LIST_FRAGMENT = "MY_BOOK_LIST_FRAGMENT" ;

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private OwnBookAdapter adapter;

    private FloatingActionButton fabAdd;

    private List<BookItem> bookItems = new ArrayList<>();

    public static Fragment newInstance() {
        return new OwnBookListFragment();
    }

    public static Fragment newInstance(Bundle args) {
        OwnBookListFragment fragment = new OwnBookListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(Const.TAG_LOG,"own books");

        super.onCreateView(inflater,container,savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        layoutManager = new LinearLayoutManager(getActivity());

        adapter = new OwnBookAdapter(this);

        setBooks(AppDatabase
                .getAppDatabase()
                .getVidDao()
                .getVidByReader(SimpleSession.getRoomReader().getId(),VidHelper.getTypicalDate().getTime()));

        adapter.setBooks(bookItems);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fabAdd = view.findViewById(R.id.fab_main);
        fabAdd.setVisibility(View.GONE);

        return view;
    }


    private void setBooks(List<RoomVid> vids){
        bookItems.clear();
        for(RoomVid vid : vids){
            bookItems.add(new BookItem(AppDatabase
                                        .getAppDatabase()
                                        .getBookDao()
                                        .getBookById(vid.getBookId())));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.own_books_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_debtor_list:
                openDebtList();
                break;

            case R.id.open_book_list:
                openMainBookList();
                break;

            case R.id.open_lender_list:
                openLendList();
                break;
        }
        return true;
    }

    private void openDebtList() {

        Fragment fragment = DebtListFragment.newInstance();

        Const.changeFragment(this,fragment);
    }

    private void openMainBookList() {

        Fragment fragment = BookListFragment.newInstance();

        Const.changeFragment(this,fragment);
    }

    private void openLendList() {

        Fragment fragment = LendListFragment.newInstance();

        Const.changeFragment(this,fragment);
    }

    @Override
    public void onClick(int adapterPosition) {
        BookItem bookItem = bookItems.get(adapterPosition);

        Bundle args = new Bundle();

        args.putLong(getString(R.string.bookId),bookItem.getId());
        args.putBoolean(getString(R.string.isOwnBook),true);

        Fragment fragment = BookDetailsFragment.newInstance(args);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(MY_BOOK_LIST_FRAGMENT)
                .commit();
    }
}
