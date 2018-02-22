package com.itis.android.lessondb.room.entity;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public enum GenreRoom {
    NUN(0),
    COMEDY(1),
    DRAMA(2),
    FANTASY(3);

    private int code;

    GenreRoom(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
