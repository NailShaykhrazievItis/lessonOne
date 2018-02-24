package com.itis.android.lessondb.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "reader",
        indices = {@Index(value = {"id"}, unique = true)},
        foreignKeys = @ForeignKey(
                entity = RoomBook.class,
                parentColumns = "id",
                childColumns = "book_id",
                onDelete = CASCADE))
public class RoomReader {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    @ColumnInfo(name = "book_id")
    private long bookId;

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

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

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
}
