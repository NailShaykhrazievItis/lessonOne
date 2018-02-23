package com.itis.android.lessondb.ui.publishers;

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
import com.itis.android.lessondb.general.Publisher;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmPublisher;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomPublisher;
import com.itis.android.lessondb.ui.AddNewActivity;
import com.itis.android.lessondb.ui.authors.AuthorsActivity;
import com.itis.android.lessondb.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PublishersActivity extends AppCompatActivity implements PublishersAdapter.OnItemClickListener {

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, PublishersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private ProgressBar progressBar;

    private PublishersAdapter adapter;

    private boolean isRoom = App.isRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initRecycler();
        getAllFromDb();
        getSupportActionBar().setTitle(R.string.publishers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_filter).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                if (isRoom) {
                    clearRoomDB();
                } else {
                    clearRealmDB();
                }
                adapter.changeDataSet(new ArrayList<>());
                return true;

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
    public void onItemClick(@NonNull Publisher item) {
        //TODO or not TODO?
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
        if (isRoom) {
            roomGetAll();
        } else {
            realmGetAll();
        }
    }

    private void realmGetAll() {
        RepositryProvider.providePublisherRepository()
                .getAllPublishers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showLoading)
                .doOnTerminate(this::hideLoading)
                .subscribe(this::changeData, this::handleError);
    }

    private void roomGetAll() {
        AppDatabase.getAppDatabase()
                .getPublisherDao()
                .getAllPublishers()
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
        adapter = new PublishersAdapter(new ArrayList<>());
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


    private void changeData(@NonNull List<RealmPublisher> publishers) {
        adapter.changeDataSet((List<Publisher>) (List<?>) publishers);
    }

    private void roomChangeData(@NonNull List<RoomPublisher> publishers) {
        adapter.changeDataSet((List<Publisher>) (List<?>) publishers);
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
