package com.itis.android.lessondb.utils;

import android.app.Fragment;

import com.itis.android.lessondb.R;

/**
 * Created by Ruslan on 23.02.2018.
 */

public class FragmentHelper {

    public static void changeFragment(Fragment lastFragment, Fragment nextFragment){
        lastFragment.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, nextFragment)
                .commit();
    }
}
