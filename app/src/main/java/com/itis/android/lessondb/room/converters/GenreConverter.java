package com.itis.android.lessondb.room.converters;

import android.arch.persistence.room.TypeConverter;

import com.itis.android.lessondb.room.entity.GenreRoom;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public class GenreConverter {

    @TypeConverter
    public int toInt(GenreRoom genre) {
        return genre.getCode();
    }

    @TypeConverter
    public GenreRoom toEnum(int code) {
        if (code == GenreRoom.NUN.getCode()) {
            return GenreRoom.NUN;
        } else if (code == GenreRoom.COMEDY.getCode()) {
            return GenreRoom.COMEDY;
        } else if (code == GenreRoom.DRAMA.getCode()) {
            return GenreRoom.DRAMA;
        } else if (code == GenreRoom.FANTASY.getCode()) {
            return GenreRoom.FANTASY;
        } else {
            throw new IllegalArgumentException("Could not recognize status");
        }
    }
}
