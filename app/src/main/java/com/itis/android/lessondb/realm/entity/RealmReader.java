package com.itis.android.lessondb.realm.entity;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tony on 3/2/2018.
 */

public class RealmReader extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private Date birthday;
    private RealmList<RealmBook> bookRealmList;
    private Date takeBook;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public RealmList<RealmBook> getBookRealmList() {
        return bookRealmList;
    }

    public void setBookRealmList(RealmList<RealmBook> bookRealmList) {
        this.bookRealmList = bookRealmList;
    }

    public Date getTakeBook() {
        return takeBook;
    }

    public void setTakeBook(Date takeBook) {
        this.takeBook = takeBook;
    }
}
