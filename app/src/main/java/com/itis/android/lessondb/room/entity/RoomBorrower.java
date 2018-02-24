package com.itis.android.lessondb.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Blaheart on 24.02.2018.
 */

@Entity(tableName = "borrower",
        indices = {@Index(value = "id", unique = true)},
        foreignKeys = @ForeignKey(
                entity = RoomBook.class,
                parentColumns = "id",
                childColumns = "book_id",
                onDelete = CASCADE
        ))
public class RoomBorrower {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private String city;

    @ColumnInfo(name = "book_id")
    private long bookId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
