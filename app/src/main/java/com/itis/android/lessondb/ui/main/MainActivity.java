package com.itis.android.lessondb.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.itis.android.lessondb.App;
import com.itis.android.lessondb.R;
import com.itis.android.lessondb.general.Book;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.ui.AddNewActivity;
import com.itis.android.lessondb.ui.DetailsActivity;
import com.itis.android.lessondb.ui.authors.AuthorsActivity;
import com.itis.android.lessondb.ui.publishers.PublishersActivity;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private ProgressBar progressBar;

    private MainAdapter adapter;

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initRecycler();
        getAllFromDb();
        getSupportActionBar().setTitle(R.string.books);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_filter).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                if (App.isRoom) {
                    clearRoomDB();
                } else {
                    clearRealmDB();
                }
                adapter.changeDataSet(new ArrayList<>());
                return true;
            case R.id.action_filter:
                item.setChecked(!item.isChecked());
                if (item.isChecked()) {
                    item.setIcon(R.drawable.ic_filter_list_white_24dp);
                    getFilteredFromDb();
                } else {
                    item.setIcon(R.drawable.ic_filter_list_black_24dp);
                    getAllFromDb();
                }
                return false;

            case R.id.action_books:
                startActivity(MainActivity.makeIntent(getApplicationContext()));
                return true;
            case R.id.action_authors:
                startActivity(AuthorsActivity.makeIntent(getApplicationContext()));
                return true;
            case R.id.action_publishers:
                startActivity(PublishersActivity.makeIntent(getApplicationContext()));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    public void onFabClicked(View vIew) {
        Intent intent = new Intent(this, AddNewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(@NonNull Book item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("item", item.getId());
        startActivity(intent);
    }

    private void clearRealmDB() {
        RepositryProvider.provideBookRepository().clearDB();
    }

    private void clearRoomDB() {
        AppDatabase.getAppDatabase().getBookDao().clearBookTable();
        AppDatabase.getAppDatabase().getAuthorDao().clearAuthorTable();
        AppDatabase.getAppDatabase().getPublisherDao().clearPublisherTable();
    }

    private void getAllFromDb() {
        if (App.isRoom) {
            roomGetAll();
        } else {
            realmGetAll();
        }
    }

    private void realmGetAll() {
        RepositryProvider.provideBookRepository()
                .getAllBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showLoading)
                .doOnTerminate(this::hideLoading)
                .subscribe(this::changeData, this::handleError);
    }

    private void roomGetAll() {
        AppDatabase.getAppDatabase()
                .getBookDao()
                .getAllBooks()
                .subscribeOn(Schedulers.io()) // this method don't need for Flowable. Flowable default work another thread
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showLoading)
                .doAfterTerminate(this::hideLoading)
                .subscribe(this::roomChangeData, this::handleError);
    }

    private void getFilteredFromDb() {
        if (App.isRoom) {
            roomGetFiltered();
        } else {
            realmGetFiltered();
        }
    }

    private void realmGetFiltered() {
        RepositryProvider.provideBookRepository()
                .getFilteredBooks(new GregorianCalendar(2015, 0, 0).getTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showLoading)
                .doOnTerminate(this::hideLoading)
                .subscribe(this::changeData, this::handleError);
    }

    private void roomGetFiltered() {
        AppDatabase.getAppDatabase()
                .getBookDao()
                .getFilteredBooks(new GregorianCalendar(2015, 0, 0).getTime())
                .subscribeOn(Schedulers.io()) // this method don't need for Flowable. Flowable default work another thread
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showLoading)
                .doAfterTerminate(this::hideLoading)
                .subscribe(this::roomChangeData, this::handleError);

    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv_main);
        fabAdd = findViewById(R.id.fab_main);
        progressBar = findViewById(R.id.pg_main);
    }

    private void initRecycler() {
        adapter = new MainAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        adapter.changeDataSet((List<Book>) (List<?>) books);
    }

    private void roomChangeData(@NonNull List<RoomBook> books) {
        adapter.changeDataSet((List<Book>) (List<?>) books);
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
}
