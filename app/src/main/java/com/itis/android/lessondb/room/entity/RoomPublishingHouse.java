package com.itis.android.lessondb.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


/**
 * Created by Admin on 23.02.2018.
 */

@Entity(tableName = "publishing_house" , indices = {@Index(value = {"id"}, unique = true)})
public class RoomPublishingHouse {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int year;
    private String phoneNumber;


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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
