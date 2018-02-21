package com.itis.android.lessondb.ui.utils;

import android.app.Fragment;

import com.itis.android.lessondb.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Ruslan on 18.02.2018.
 */

//обычный класс констант и прочего общего кода
public class Const {

    public static final String TAG_LOG = "TAG";

    public static final boolean isRoom = true;

    private final static int FILTER_YEAR = 1950;

    public static long getFilterYear(){
        Date date = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR,FILTER_YEAR);
        return calendar.getTime().getTime();
    }

    public static void changeFragment(Fragment lastFragment, Fragment nextFragment){
        lastFragment.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, nextFragment)
                .commit();
    }

}
