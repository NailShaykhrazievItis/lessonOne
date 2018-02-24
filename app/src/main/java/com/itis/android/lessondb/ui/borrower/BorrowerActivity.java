package com.itis.android.lessondb.ui.borrower;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.Genre;
import com.itis.android.lessondb.realm.entity.RealmBorrower;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBorrower;
import com.itis.android.lessondb.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BorrowerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private BorrowerAdapter adapter;

    private boolean isRoom = true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_borrower, menu);
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

            case R.id.action_main: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower);
        initViews();
        initRecycler();
        if (isRoom) {
            roomGetAll();
        } else {
            realmGetAll();
        }
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void realmGetAll() {
        RepositryProvider.provideBorrowerRepository()
                .getAllBorrowers()
                .doOnSubscribe(this::showLoading)
                .doOnTerminate(this::hideLoading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::changeData, this::handleError);
    }

    private void roomGetAll() {
        AppDatabase.getAppDatabase()
                .getBorrowerDAO()
                .getAllBorrowers()
                .doOnSubscribe(this::showLoading)
                .doAfterTerminate(this::hideLoading)
                .subscribeOn(Schedulers.io()) // this method don't need for Flowable. Flowable default work another thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::roomChangeData, this::handleError);
    }

    private void changeData(@NonNull List<RealmBorrower> books) {
//        adapter.changeDataSet(books);
    }

    private void roomChangeData(@NonNull List<RoomBorrower> books) {
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

    private void initViews() {
        recyclerView = findViewById(R.id.rv_borrower);
        progressBar = findViewById(R.id.pg_borrower);
    }

    private void initRecycler() {
        adapter = new BorrowerAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private void clearRealmDB() {
        RepositryProvider.provideBorrowerRepository().clearDB();
    }

    private void clearRoomDB() {
        AppDatabase.getAppDatabase().getBorrowerDAO().clearBorrowers();
        AppDatabase.getAppDatabase().getBookDao().clearBookTable();
        AppDatabase.getAppDatabase().getAuthorDao().clearAuthorTable();
    }
}
