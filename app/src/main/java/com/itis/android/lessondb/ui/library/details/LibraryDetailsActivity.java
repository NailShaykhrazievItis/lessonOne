package com.itis.android.lessondb.ui.library.details;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositoryProvider;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.entity.RealmLibrary;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomLibrary;
import com.itis.android.lessondb.ui.base.details.BaseDetailsActivity;

import java.util.List;

import io.realm.RealmList;

public class LibraryDetailsActivity extends BaseDetailsActivity {
    private TextView tvName;
    private TextView tvAddress;
    private ListView lvBooks;

    @Override
    protected void initFields() {
        tvName = findViewById(R.id.tv_name);
        tvAddress = findViewById(R.id.tv_address);
        lvBooks = findViewById(R.id.lv_books);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_library_details;
    }

    @Override
    protected void realmFlow(long id) {
        RealmLibrary library = RepositoryProvider.provideLibraryRepository().getLibraryById(id);
        tvName.setText(library.getName());
        if (library.getAddress() != null) {
            tvAddress.setText(library.getAddress());
        }

        if (library.getBookRealmList() != null) {
            String[] bookNames = getBookNames(library.getBookRealmList());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    bookNames);
            lvBooks.setAdapter(adapter);
        }
    }

    @Override
    protected void roomFlow(long id) {
        RoomLibrary library = AppDatabase.getAppDatabase().getLibraryDao().getById(id);
        tvName.setText(library.getName());
        if (library.getAddress() != null) {
            tvAddress.setText(library.getAddress());
        }

        List<RoomBook> bookList = AppDatabase.getAppDatabase().getBookDao()
                .getAllBooksByLibraryId(id);
        if (bookList != null) {
            String[] bookNames = getBookNames(bookList);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    bookNames);
            lvBooks.setAdapter(adapter);
        }
    }

}
