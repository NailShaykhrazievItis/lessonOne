package com.itis.android.lessondb.realm.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by a9 on 20.02.18.
 */

public class RealmPublisher extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private String address;

    private RealmList<RealmBook> bookRealmList;

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

    @Override
    public String toString() {
        return "RealmPublisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", bookRealmList=" + bookRealmList +
                '}';
    }
}
