package com.itis.android.lessondb.utils;

import android.widget.Toast;

import com.itis.android.lessondb.App;

/**
 * Created by Ruslan on 23.02.2018.
 */

public class ViewHelper {

    public static void showToast(String message){
        Toast.makeText(App.getContext(),message,Toast.LENGTH_SHORT).show();
    }
}
