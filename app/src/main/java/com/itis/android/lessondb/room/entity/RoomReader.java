package com.itis.android.lessondb.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Ruslan on 16.02.2018.
 */

//класс читателя(юзера)
@Entity(tableName = "reader",
        indices = {@Index(value = {"id"}, unique = true),
                    @Index(value = {"username"}, unique = true),
                    @Index(value = {"username","password"})})
public class RoomReader {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String username;

    private String password;

    private String city;

    private Date birthday;

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
}
