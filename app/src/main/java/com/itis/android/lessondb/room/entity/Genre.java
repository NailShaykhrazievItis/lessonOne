package com.itis.android.lessondb.room.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public enum Genre {
    FANTASY("fantasy"),
    COMEDY("comedy"),
    DRAMA("drama"),
    NUN("undefined");

    private String genreString;

    Genre(String genreString) {
        this.genreString = genreString;
    }

    public static List<String> getValuesAsArray() {
        List<String> valuesString = new ArrayList<>();
        for(Genre genre: values()) {
            valuesString.add(genre.getGenreString());
        }
        return valuesString;
    }

    public static Genre getGenreByString(String genreString) {
        for (Genre genre: values()) {
            if (genre.genreString.equals(genreString))
                return genre;
        }
        return null;
    }

    public String getGenreString() {
        return genreString;
    }
}
