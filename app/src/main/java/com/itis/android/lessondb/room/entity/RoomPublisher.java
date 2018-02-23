package com.itis.android.lessondb.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.itis.android.lessondb.general.Publisher;

/**
 * Created by a9 on 20.02.18.
 */

@Entity(tableName = "publisher",indices = {@Index(value = {"id"},unique = true)})
public class RoomPublisher implements Publisher {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private String address;

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


    @Override
    public String toString() {
        return "RoomPublisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
