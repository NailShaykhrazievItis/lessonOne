package com.itis.android.lessondb.ui.author;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.general.Author;
import com.itis.android.lessondb.realm.RepositoryProvider;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.ui.author.addnew.AuthorAddNewActivity;
import com.itis.android.lessondb.ui.author.details.AuthorDetailsActivity;
import com.itis.android.lessondb.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthorActivity extends BaseActivity<Author> {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private ProgressBar progressBar;

    private AuthorAdapter adapter;

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
    public void onItemClick(@NonNull Author item) {
        Intent intent = new Intent(this, AuthorDetailsActivity.class);
        intent.putExtra("item", item.getId());
        startActivity(intent);
    }

    public void onFabClicked(View vIew) {
        Intent intent = new Intent(this, AuthorAddNewActivity.class);
        startActivity(intent);
    }

    private void clearRealmDB() {
        RepositoryProvider.provideAuthorRepository().clearDB();
    }

    private void clearRoomDB() {
        AppDatabase.getAppDatabase().getAuthorDao().clearAuthorTable();
    }

    @Override
    protected void realmGetAll() {
        RepositoryProvider.provideAuthorRepository()
                .getAllAuthors()
//                .doOnSubscribe(this::showLoading)
//                .doOnTerminate(this::hideLoading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::changeData, this::handleError);
    }

    @Override
    protected void initContent() {
        initRecycler();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_author;
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
    }

    private void initRecycler() {
        adapter = new AuthorAdapter(new ArrayList<>());
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
    }

    private void changeData(@NonNull List<RealmAuthor> authors) {
        adapter.changeDataSet(authors);
    }

    private void roomChangeData(@NonNull List<RoomBook> authors) {
        // change generic to use Rooom
//        adapter.changeDataSet(authors);
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
