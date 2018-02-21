package com.itis.android.lessondb.realm.entity;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ruslan on 16.02.2018.
 */

public class RealmReader extends RealmObject{

    @PrimaryKey
    private long id;

    private String username;

    private String password;

    private String city;

    private Date birthday;

    private RealmList<RealmVid> takenBookList;

    private RealmList<RealmVid> givenBookList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public RealmList<RealmVid> getTakenBookList() {
        return takenBookList;
    }

    public void setTakenBookList(RealmList<RealmVid> takenBookList) {
        this.takenBookList = takenBookList;
    }

    public RealmList<RealmVid> getGivenBookList() {
        return givenBookList;
    }

    public void setGivenBookList(RealmList<RealmVid> givenBookList) {
        this.givenBookList = givenBookList;
    }
}
