package com.itis.android.lessondb.ui.base.addnew;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.itis.android.lessondb.R;
import com.itis.android.lessondb.realm.RepositoryProvider;
import com.itis.android.lessondb.realm.entity.RealmLibrary;
import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.ui.base.DatabaseTypeConstant;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public abstract class AddNewActivity extends AppCompatActivity implements DatabaseTypeConstant {
    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    protected abstract void onButtonAddClicked(View view);
}
