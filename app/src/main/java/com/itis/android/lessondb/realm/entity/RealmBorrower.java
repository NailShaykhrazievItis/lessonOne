package com.itis.android.lessondb.realm.entity;

import android.arch.persistence.room.PrimaryKey;

import io.realm.RealmObject;

/**
 * Created by Blaheart on 24.02.2018.
 */

public class RealmBorrower extends RealmObject {

    @PrimaryKey
    private long id;

    private String name;
    private String city;

    private RealmBook book;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public RealmBook getBook() {
        return book;
    }

    public void setBook(RealmBook book) {
        this.book = book;
    }
}
