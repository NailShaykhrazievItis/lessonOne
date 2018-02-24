package com.itis.android.lessondb.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.ui.author.AuthorActivity;
import com.itis.android.lessondb.ui.library.LibraryActivity;
import com.itis.android.lessondb.ui.main.MainActivity;

/**
 * Created by valera071998@gamil.com on 20.02.2018.
 */

public abstract class BaseActivity<T> extends AppCompatActivity implements OnListItemClickListener<T>, DatabaseTypeConstant {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenuResource(), menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initViews();
        initContent();
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

    protected abstract void roomGetAll();

    protected abstract void realmGetAll();

    protected abstract void initContent();

    protected int getMenuResource() {
        return R.menu.menu_main;
    }

    protected abstract int getContentView();

    protected abstract void initViews();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                clear();
                return true;
            case R.id.action_show_authors:
                startActivity(AuthorActivity.class);
                return true;
            case R.id.action_show_books:
                startActivity(MainActivity.class);
                return true;
            case R.id.action_show_libraries:
                startActivity(LibraryActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public abstract void onItemClick(@NonNull T item);

    protected abstract void clear();

    private void startActivity(Class activity) {
        Intent intentLibrary = new Intent(this, activity);
        startActivity(intentLibrary);
    }
}
