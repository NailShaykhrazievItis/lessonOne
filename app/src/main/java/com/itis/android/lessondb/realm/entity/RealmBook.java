package com.itis.android.lessondb.realm.entity;



import com.itis.android.lessondb.general.Book;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public class RealmBook extends RealmObject implements Book {

    @PrimaryKey
    private long id;
    private String title;
    private RealmAuthor realmAuthor;
    private RealmGenre genre;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmAuthor getRealmAuthor() {
        return realmAuthor;
    }

    public void setRealmAuthor(RealmAuthor realmAuthor) {
        this.realmAuthor = realmAuthor;
    }

    public RealmGenre getGenre() {
        return genre;
    }

    public void setGenre(RealmGenre genre) {
        this.genre = genre;
    }
}
