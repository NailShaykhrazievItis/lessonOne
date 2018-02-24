package com.itis.android.lessondb.ui.main;

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
import com.itis.android.lessondb.general.Book;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.Genre;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.ui.AddNewActivity;
import com.itis.android.lessondb.ui.DetailsActivity;
import com.itis.android.lessondb.ui.borrower.BorrowerActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private ProgressBar progressBar;

    private MainAdapter adapter;

    private boolean isRoom = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initRecycler();
        if (isRoom) {
            roomGetAll();
        } else {
            realmGetAll();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_filter);

        Spinner filterSpinner = (Spinner) item.getActionView();

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String genreString = parent.getItemAtPosition(position).toString();
                if (!"NUN".equals(genreString)) {
                    if (isRoom) {
                        com.itis.android.lessondb.room.entity.Genre roomGenre = com.itis.android.lessondb.room.entity.Genre.valueOf(genreString);
                        roomGetByGenre(roomGenre);
                    } else {
                        Genre realmGenre = Genre.valueOf(genreString);
                        realmGetByGenre(realmGenre);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        filterSpinner.setAdapter(adapter);

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

            case R.id.action_borrower: {
                Intent intent = new Intent(this, BorrowerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
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

    private void realmGetByGenre(Genre genre) {
        RepositryProvider.provideBookRepository()
                .getBooksByGenre(genre)
                .doOnSubscribe(this::showLoading)
                .doOnTerminate(this::hideLoading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::changeData, this::handleError);
    }

    private void roomGetByGenre(com.itis.android.lessondb.room.entity.Genre genre) {
        List<RoomBook> books = AppDatabase.getAppDatabase()
                .getBookDao()
                .getBooksByGenre(genre);
                roomChangeData(books);
                //Throws exception for some reason with Rx
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
}
