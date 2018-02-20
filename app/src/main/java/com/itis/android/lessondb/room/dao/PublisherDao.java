package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.RoomPublisher;

/**
 * Created by a9 on 20.02.18.
 */

public interface PublisherDao {

    @Query("SELECT * FROM publisher WHERE id=:publisherId LIMIT 1")
    RoomPublisher getPublisherById(long publisherId);

    @Insert
    long insertPublisher(RoomPublisher publisher);

    @Query("DELETE FROM publisher")
    void clearPublisherTable();
}
