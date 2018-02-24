package com.itis.android.lessondb.ui.base.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositoryProvider;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.ui.base.DatabaseTypeConstant;

import java.text.SimpleDateFormat;
import java.util.List;

import io.realm.RealmList;

public abstract class BaseDetailsActivity extends AppCompatActivity implements DatabaseTypeConstant {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initFields();
        long id = getIntent().getLongExtra("item", 0L);
        if (isRoom) {
            roomFlow(id);
        } else {
            realmFlow(id);
        }
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    protected abstract void initFields();

    public abstract int getContentView();

    protected abstract void realmFlow(long id);

    protected abstract void roomFlow(long id);

    protected String[] getBookNames(RealmList<RealmBook> bookRealmList) {
        String[] bookNames = new String[bookRealmList.size()];
        for (int i = 0; i < bookRealmList.size(); i++) {
            bookNames[i] = bookRealmList.get(i).getTitle();
        }
        return bookNames;
    }
    protected String[] getBookNames(List<RoomBook> bookRoomList) {
        String[] bookNames = new String[bookRoomList.size()];
        for (int i = 0; i < bookRoomList.size(); i++) {
            bookNames[i] = bookRoomList.get(i).getTitle();
        }
        return bookNames;
    }
}
