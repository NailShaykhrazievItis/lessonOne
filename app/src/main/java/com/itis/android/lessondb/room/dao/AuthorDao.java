package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.RoomAuthor;

import java.util.List;

/**
 * Created by Nail Shaykhraziev on 13.02.2018.
 */
@Dao
public interface AuthorDao {

    @Query("SELECT * FROM author WHERE id = :authorId LIMIT 1")
    RoomAuthor getAuthorById(long authorId);

    @Query("SELECT id FROM author WHERE name = :name")
    long getAuthorIdByName(String name);

    @Insert
    long insertAuthor(RoomAuthor author); //room can return id of entity

    @Query("DELETE FROM author")
    void clearAuthorTable();

    @Query("SELECT * FROM author")
    List<RoomAuthor> getAllAuthors();
}
