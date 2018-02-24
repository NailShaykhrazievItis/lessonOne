package com.itis.android.lessondb.ui.fragments.lists.book.main_book_list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ProgressBar;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.general.Book;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.ui.base.BaseFragment;
import com.itis.android.lessondb.ui.fragments.single.AddNewBookFragment;
import com.itis.android.lessondb.ui.fragments.lists.book.BookDetailsFragment;
import com.itis.android.lessondb.ui.fragments.lists.book.own_book_list.OwnBookListFragment;
import com.itis.android.lessondb.ui.fragments.lists.vid.debt_book_list.DebtListFragment;
import com.itis.android.lessondb.ui.fragments.lists.vid.lend_book_list.LendListFragment;
import com.itis.android.lessondb.utils.Const;
import com.itis.android.lessondb.utils.DateUtil;
import com.itis.android.lessondb.utils.FragmentHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.itis.android.lessondb.utils.Const.isRoom;

public class BookListFragment extends BaseFragment implements BookAdapter.OnItemClickListener, View.OnClickListener {

    private static final String FRAGMENT_BOOK_LIST_TAG = "FRAGMENT_BOOK_LIST_TAG";

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private ProgressBar progressBar;

    private BookAdapter adapter;

    public static Fragment newInstance() {

        return new BookListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(Const.TAG_LOG,"all books");

        super.onCreateView(inflater,container,savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_book_list, container, false);



        initViews(view);
        initRecycler();
        if (isRoom) {
            roomGetAll();
        } else {
            realmGetAll();
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.all_book_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                clearDB();
                break;

            case R.id.open_debtor_list:
                openDebtList();
                break;

            case R.id.open_my_book_list:
                openReaderBookList();
                break;

            case R.id.open_lender_list:
                openLendList();
                break;

            case R.id.filter_books:
                filterBooks();
                break;

        }
        return true;
    }

    private void openDebtList() {

        Fragment fragment = DebtListFragment.newInstance();

        FragmentHelper.changeFragment(this,fragment);
    }

    private void openReaderBookList() {

        Fragment fragment = OwnBookListFragment.newInstance();

        FragmentHelper.changeFragment(this,fragment);
    }

    private void openLendList() {

        Fragment fragment = LendListFragment.newInstance();

        FragmentHelper.changeFragment(this,fragment);
    }

    private void filterBooks(){
        if (isRoom) {
            roomFilter();
        } else {
            realmFilter();
        }
    }

    private void roomFilter(){
        List<RoomBook> books = AppDatabase.getAppDatabase().getBookDao().findBooksBeforeYear(DateUtil.getFilterYear());
        if(books != null){
            roomChangeData(books);
        }
    }

    private void realmFilter(){
    }

    public void onFabClicked(View vIew) {

        Fragment fragment = AddNewBookFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(FRAGMENT_BOOK_LIST_TAG)
                .commit();
    }

    @Override
    public void onItemClick(@NonNull Book item) {

        Bundle args = new Bundle();
        args.putLong(getString(R.string.bookId), item.getId());
        args.putBoolean(getString(R.string.isOwnBook),false);

        Fragment fragment = BookDetailsFragment.newInstance(args);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(FRAGMENT_BOOK_LIST_TAG)
                .commit();
    }

    private void clearDB(){
        if (isRoom) {
            clearRoomDB();
        } else {
            clearRealmDB();
        }
        adapter.changeDataSet(new ArrayList<>());
    }

    private void clearRealmDB() {
        RepositryProvider.provideBookRepository().clearDB();
    }

    private void clearRoomDB() {
        AppDatabase.getAppDatabase().getBookDao().clearBookTable();
        AppDatabase.getAppDatabase().getAuthorDao().clearAuthorTable();
    }

    private void realmGetAll() {
        RepositryProvider.provideBookRepository()
                .getAllBooks()
                .doOnSubscribe(this::showLoading)
                .doOnTerminate(this::hideLoading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::changeData, this::handleError);
    }

    private void roomGetAll() {
        AppDatabase.getAppDatabase()
                .getBookDao()
                .getAllBooks()
                .doOnSubscribe(this::showLoading)
                .doAfterTerminate(this::hideLoading)
                .subscribeOn(Schedulers.io()) // this method don't need for Flowable. Flowable default work another thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::roomChangeData, this::handleError);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.rv_main);
        fabAdd = view.findViewById(R.id.fab_main);
        progressBar = view.findViewById(R.id.pg_main);

        fabAdd.setOnClickListener(this);
    }

    private void initRecycler() {
        adapter = new BookAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter.onAttachedToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabAdd.getVisibility() == View.VISIBLE) {
                    fabAdd.hide();
                } else if (dy < 0 && fabAdd.getVisibility() != View.VISIBLE) {
                    fabAdd.show();
                }
            }
        });
    }

    private void changeData(@NonNull List<RealmBook> books) {
//        adapter.changeDataSet(books);
    }

    private void roomChangeData(@NonNull List<RoomBook> books) {
        adapter.changeDataSet(books);
    }

    private void handleError(Throwable throwable) {
        Log.e("HandleError: ", throwable.getMessage());
    }

    private void showLoading(Disposable disposable) {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @NonNull
    @Deprecated
    private List<String> generateNames() {
        List<String> names = new ArrayList<>();
        names.add("War and Piece");
        names.add("The Lord of the Rings");
        names.add("The song of ice and fire");
        names.add("Hobbit");
        names.add("The Divine Comedy");
        names.add("Hamlet");
        names.add("Alice's Adventures in Wonderland");
        names.add("Gulliver's Travels");
        names.add("Harry Potter");
        names.add("The Count Of Monte Cristo");
        names.add("Do Androids Dream of Electric Sheep?");
        names.add("Witcher");
        names.add("Romeo and Juliet");
        return names;
    }

    @Override
    public void onClick(View v) {
        onFabClicked(v);
    }
}
