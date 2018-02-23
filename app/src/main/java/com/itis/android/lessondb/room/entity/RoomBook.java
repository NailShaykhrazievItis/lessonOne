package com.itis.android.lessondb.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.itis.android.lessondb.general.Book;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */
@Entity(tableName = "book",
        indices = {@Index(value = {"id"}, unique = true)},
        foreignKeys = {
                @ForeignKey(entity = RoomAuthor.class,
                        parentColumns = "id",
                        childColumns = "author_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = RoomPublishing.class,
                        parentColumns = "id",
                        childColumns = "publish_id")
        })
public class RoomBook implements Book {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "title")
    private String title;

    private String desc;

    private Date releaseDate;

    private Genre genre = Genre.NUN;

    @ColumnInfo(name = "author_id")
    private long authorId;

    @ColumnInfo(name = "publish_id")
    private long publishId;

    public long getPublishId() {
        return publishId;
    }

    public void setPublishId(long publishId) {
        this.publishId = publishId;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
}
