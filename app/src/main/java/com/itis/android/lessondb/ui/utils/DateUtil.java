package com.itis.android.lessondb.ui.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ruslan on 18.02.2018.
 */

public class DateUtil {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        DateUtil.dateFormat = dateFormat;
    }

    public static String getDateInStr(Date date){
        return dateFormat.format(date);
    }

    public static Date getDateFromStr(String dateStr){
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            Log.d(Const.TAG_LOG,"DateTransform failed");
        }
        return null;
    }
}
