package com.itis.android.lessondb.realm.entity;

import com.itis.android.lessondb.general.Library;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by valera071998@gmail.com on 19.02.2018.
 */

public class RealmLibrary extends RealmObject implements Library {
    @PrimaryKey
    private long id;
    @Required
    private String name;
    private String address;
    private RealmList<RealmBook> bookRealmList = new RealmList<>();

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RealmList<RealmBook> getBookRealmList() {
        return bookRealmList;
    }

    public void setBookRealmList(RealmList<RealmBook> bookRealmList) {
        this.bookRealmList = bookRealmList;
    }
}
