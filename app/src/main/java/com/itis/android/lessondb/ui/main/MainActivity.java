package com.itis.android.lessondb.ui.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.general.Book;
import com.itis.android.lessondb.realm.RepositoryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.ui.base.BaseActivity;
import com.itis.android.lessondb.ui.base.BaseAdapter;
import com.itis.android.lessondb.ui.main.addnew.BookAddNewActivity;
import com.itis.android.lessondb.ui.main.details.MainDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity<Book> {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private ProgressBar progressBar;
    private SearchView searchView;

    private BaseAdapter<RealmBook, MainItemHolder> adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void clear() {
        if (isRoom) {
            clearRoomDB();
        } else {
            clearRealmDB();
        }
        adapter.changeDataSet(new ArrayList<>());
    }

    @Override
    public void onItemClick(@NonNull Book item) {
        Intent intent = new Intent(this, MainDetailsActivity.class);
        intent.putExtra("item", item.getId());
        startActivity(intent);
    }

    public void onFabClicked(View vIew) {
        Intent intent = new Intent(this, BookAddNewActivity.class);
        startActivity(intent);
    }

    private void clearRealmDB() {
        RepositoryProvider.provideBookRepository().clearDB();
    }

    private void clearRoomDB() {
        AppDatabase.getAppDatabase().getBookDao().clearBookTable();
    }

    @Override
    protected void realmGetAll() {
        RepositoryProvider.provideBookRepository()
                .getAllBooks()
//                .doOnSubscribe(this::showLoading)
//                .doOnTerminate(this::hideLoading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::changeData, this::handleError);
    }

    @Override
    protected void initContent() {
        initRecyclerAndSearch();
    }

    @Override
    protected void roomGetAll() {
        AppDatabase.getAppDatabase()
                .getBookDao()
                .getAllBooks()
//                .doOnSubscribe(this::showLoading)
//                .doAfterTerminate(this::hideLoading)
                .subscribeOn(Schedulers.io()) // this method don't need for Flowable. Flowable default work another thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::roomChangeData, this::handleError);
    }

    @Override
    protected void initViews() {
        recyclerView = findViewById(R.id.rv_main);
        fabAdd = findViewById(R.id.fab_main);
        progressBar = findViewById(R.id.pg_main);
        searchView = findViewById(R.id.search);
    }

    private void initRecyclerAndSearch() {
        adapter = new MainAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.onAttachedToRecyclerView(recyclerView);
        adapter.setOnListItemClickListener(this);
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((MainAdapter) adapter).getFilter().filter(newText);
                return false;
            }
        });
        searchView.setActivated(true);
        searchView.setQueryHint(getResources().getString(R.string.hint_search));
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();
    }

    private void changeData(@NonNull List<RealmBook> books) {
        adapter.changeDataSet(books);
    }

    private void roomChangeData(@NonNull List<RoomBook> books) {
        // change generic to use Room
//        adapter.changeDataSet(books);
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
}
