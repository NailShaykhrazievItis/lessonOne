package com.itis.android.lessondb.ui.main.fragments.lists.vid_lists.debt_book_list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.itis.android.lessondb.ui.main.fragments.lists.book_lists.main_book_list.BookListFragment;
import com.itis.android.lessondb.ui.main.fragments.lists.book_lists.own_book_list.OwnBookListFragment;
import com.itis.android.lessondb.ui.main.fragments.lists.vid_lists.VidDetailsFragment;
import com.itis.android.lessondb.ui.main.fragments.lists.vid_lists.VidItem;
import com.itis.android.lessondb.ui.main.fragments.lists.vid_lists.lend_book_list.LendListFragment;
import com.itis.android.lessondb.ui.utils.Const;
import com.itis.android.lessondb.ui.utils.FragmentHelper;
import com.itis.android.lessondb.ui.utils.OnItemClickListener;
import com.itis.android.lessondb.ui.utils.SimpleSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class DebtListFragment extends BaseFragment implements OnItemClickListener {

    private static final String DEBT_LIST_FRAGMENT = "DEBT_LIST_FRAGMENT" ;

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private DebtAdapter adapter;

    private List<VidItem> vidItems = new ArrayList<>();

    private final boolean isLender = true;

    public static Fragment newInstance() {
        return new DebtListFragment();
    }

    public static Fragment newInstance(Bundle args) {
        DebtListFragment fragment = new DebtListFragment();
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

        Log.d(Const.TAG_LOG,"debt books");

        super.onCreateView(inflater,container,savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_vid_list, container, false);

        layoutManager = new LinearLayoutManager(getActivity());

        adapter = new DebtAdapter(this);

        setVidItems(AppDatabase.getAppDatabase().getVidDao().getVidByDebtor(SimpleSession.getRoomReader().getId()));

        adapter.setVidItems(vidItems);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_vid);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void setVidItems(List<RoomVid> vids){
        vidItems.clear();
        for(RoomVid vid : vids){
            vidItems.add(new VidItem(vid,isLender));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.debt_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_lender_list:
                openLendList();
                break;

            case R.id.open_book_list:
                openMainBookList();
                break;

            case R.id.open_my_book_list:
                openOwnBookList();
                break;
        }
        return true;
    }

    private void openLendList() {

        Fragment fragment = LendListFragment.newInstance();

        FragmentHelper.changeFragment(this,fragment);
    }

    private void openMainBookList() {

        Fragment fragment = BookListFragment.newInstance();

        FragmentHelper.changeFragment(this,fragment);
    }

    private void openOwnBookList() {

        Fragment fragment = OwnBookListFragment.newInstance();

        FragmentHelper.changeFragment(this,fragment);
    }

    @Override
    public void onClick(int adapterPosition) {
        VidItem alarm = vidItems.get(adapterPosition);

        Bundle args = new Bundle();

        args.putLong(getString(R.string.vidId),alarm.getId());
        args.putString(getString(R.string.readerRole),getString(R.string.lender));

        Fragment fragment = VidDetailsFragment.newInstance(args);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(DEBT_LIST_FRAGMENT)
                .commit();
    }
}
