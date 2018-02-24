package com.itis.android.lessondb.ui.library.addnew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositoryProvider;
import com.itis.android.lessondb.realm.entity.RealmLibrary;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomLibrary;
import com.itis.android.lessondb.ui.base.addnew.AddNewActivity;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class LibraryAddNewActivity extends AddNewActivity {

    private EditText etName;
    private EditText etAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_library);
        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);

        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);
    }

    @Override
    protected void onButtonAddClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                String name = etName.getText().toString().trim();
                String address = etAddress.getText().toString().trim();

                if (isRoom) {
                    roomFlow(name, address);
                } else {
                    realmFlow(name, address);
                }
                Toast.makeText(this, getString(R.string.add_book), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void realmFlow(String name, String address) {
        RealmLibrary library = new RealmLibrary();
        library.setName(name);
        library.setAddress(address);

        RepositoryProvider.provideLibraryRepository().insert(library);
    }

    private void roomFlow(String name, String address) {
        RoomLibrary library = new RoomLibrary();
        library.setName(name);
        library.setAddress(address);

        AppDatabase.getAppDatabase().getLibraryDao().insert(library);
    }
}
