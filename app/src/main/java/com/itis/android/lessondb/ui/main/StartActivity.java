package com.itis.android.lessondb.ui.main;

import android.app.Fragment;

import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.ui.base.FragmentHostActivity;
import com.itis.android.lessondb.ui.main.fragments.StartFragment;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class StartActivity extends FragmentHostActivity {

    @Override
    protected Fragment getFragment() {
        return StartFragment.newInstance();
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

}
