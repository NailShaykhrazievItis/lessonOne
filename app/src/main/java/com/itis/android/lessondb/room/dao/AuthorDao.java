package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.RoomAuthor;

/**
 * Created by Nail Shaykhraziev on 13.02.2018.
 */
@Dao
public interface AuthorDao {

    @Query("SELECT * FROM author WHERE id = :authorId LIMIT 1")
    RoomAuthor getAuthorById(long authorId);

    @Query("SELECT * FROM author WHERE name = :authorName LIMIT 1")
    RoomAuthor getAuthorByName(String authorName);

    @Insert
    long insertAuthor(RoomAuthor author); //room can return id of entity

    @Query("DELETE FROM author")
    void clearAuthorTable();
}
