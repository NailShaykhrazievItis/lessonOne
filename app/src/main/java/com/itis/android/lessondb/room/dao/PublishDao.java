package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.RoomPublishing;

/**
 * Created by User on 18.02.2018.
 */

@Dao
public interface PublishDao {

    @Query("SELECT * FROM publishing WHERE id = :publishId LIMIT 1")
    RoomPublishing getPublishById(long publishId);

    @Insert
    long insertPublish(RoomPublishing publish); //room can return id of entity

    @Query("DELETE FROM publishing")
    void clearPublishTable();

}
