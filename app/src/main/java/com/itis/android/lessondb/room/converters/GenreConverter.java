package com.itis.android.lessondb.room.converters;

import android.arch.persistence.room.TypeConverter;

import com.itis.android.lessondb.room.entity.Genre;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public class GenreConverter {

    @TypeConverter
    public String toString(Genre genre) {
        return genre.getGenreString();
    }

    @TypeConverter
    public Genre toEnum(String name) {
        return Genre.valueOf(name);
    }
}
