package com.itis.android.lessondb.ui.author.addnew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositoryProvider;
import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.ui.base.addnew.DateAddNewActivity;
import com.itis.android.lessondb.ui.base.datepicker.DatePickerFragment;
import com.itis.android.lessondb.ui.base.datepicker.DatePickerResolver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class AuthorAddNewActivity extends DateAddNewActivity {

    private EditText etName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_author);
        etName = findViewById(R.id.et_name);

        btnDate = findViewById(R.id.btn_date_picker);
        btnDate.setOnClickListener(this::onButtonAddClicked);

        findViewById(R.id.btn_add).setOnClickListener(this::onButtonAddClicked);
    }

    @Override
    protected void onButtonAddClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                String name = etName.getText().toString().trim();

                if (isRoom) {
                    roomFlow(name, date);
                } else {
                    realmFlow(name, date);
                }
                Toast.makeText(this, getString(R.string.add_book), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_date_picker:
                datePickAction();
        }
    }

    private void realmFlow(String name, Date date) {
        RealmAuthor author = new RealmAuthor();
        author.setName(name);
        author.setBirthday(date);

        RepositoryProvider.provideAuthorRepository().insert(author);
    }

    private void roomFlow(String name, Date date) {
        RoomAuthor author = new RoomAuthor();
        author.setName(name);
        author.setBirthday(date);

        AppDatabase.getAppDatabase().getAuthorDao().insertAuthor(author);
    }
}
