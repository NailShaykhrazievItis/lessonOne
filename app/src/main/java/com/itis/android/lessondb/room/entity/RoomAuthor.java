package com.itis.android.lessondb.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */
@Entity(tableName = "author", indices = {@Index(value = {"id"}, unique = true)})
public class RoomAuthor {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private Date birthday;

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
}
