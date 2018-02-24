package com.itis.android.lessondb.realm.entity;

import com.itis.android.lessondb.general.Author;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public class RealmAuthor extends RealmObject implements Author {
    @PrimaryKey
    private long id;
    @Required
    private String name;
    private Date birthday;
    private RealmList<RealmBook> bookRealmList = new RealmList<>();

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
}
