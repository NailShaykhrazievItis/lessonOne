package com.itis.android.lessondb.realm.entity;

import io.realm.RealmObject;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public class RealmGenre extends RealmObject {

    private String enumDescription;

    public void saveEnum(Genre val) {
        this.enumDescription = val.toString();
    }

    public Genre getEnum() {
        return (enumDescription != null) ? Genre.valueOf(enumDescription) : null;
    }
}
