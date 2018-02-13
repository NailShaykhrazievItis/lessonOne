package com.itis.android.lessondb.room.converters;

import android.arch.persistence.room.TypeConverter;

import com.itis.android.lessondb.room.entity.Genre;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public class GenreConverter {

    @TypeConverter
    public int toInt(Genre genre) {
        return genre.getCode();
    }

    @TypeConverter
    public Genre toEnum(int code) {
        if (code == Genre.NUN.getCode()) {
            return Genre.NUN;
        } else if (code == Genre.COMEDY.getCode()) {
            return Genre.COMEDY;
        } else if (code == Genre.DRAMA.getCode()) {
            return Genre.DRAMA;
        } else if (code == Genre.FANTASY.getCode()) {
            return Genre.FANTASY;
        } else {
            throw new IllegalArgumentException("Could not recognize status");
        }
    }
}
