package com.itis.android.lessondb.room.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public class DateConverter {

    @TypeConverter
    public Date fromLong(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long toDate(Date date) {
        return date == null ? null : date.getTime();
    }
}
