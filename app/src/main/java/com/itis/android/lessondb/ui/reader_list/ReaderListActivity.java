package com.itis.android.lessondb.ui.reader_list;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomReader;
import com.itis.android.lessondb.ui.main.MainAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class ReaderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ReaderListAdapter adapter;

    private boolean isRoom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_list);
        recyclerView = findViewById(R.id.rv_reader);
        initRecycler();
        if (isRoom) {
            roomChangeData(roomGetAll());
        } else {
            changeData(realmGetAll());
        }
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }


    private void initRecycler() {
        adapter = new ReaderListAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private List<RealmReader> realmGetAll() {
        return RepositryProvider.provideReaderReposirory()
                .getAllReaders();
    }

    private List<RoomReader> roomGetAll() {
        return AppDatabase.getAppDatabase()
                .getReaderDao()
                .getAllReaders();
    }

    private void changeData(@NonNull List<RealmReader> books) {
        adapter.changeDataSet(books);
    }

    private void roomChangeData(@NonNull List<RoomReader> readers) {
       // adapter.changeDataSet(readers);
    }
}
