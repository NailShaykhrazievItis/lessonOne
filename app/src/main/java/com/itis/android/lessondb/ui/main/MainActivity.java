package com.itis.android.lessondb.ui.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.ui.AddNewActivity;
import com.itis.android.lessondb.ui.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;

    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_main);
        fabAdd = findViewById(R.id.fab_main);
        initRecycler();
        adapter.changeDataSet(generateNames());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFabClicked(View vIew) {
        Intent intent = new Intent(this, AddNewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(@NonNull String item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @NonNull
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
