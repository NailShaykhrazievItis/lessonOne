package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.RoomPublisher;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by a9 on 20.02.18.
 */

@Dao
public interface PublisherDao {

    @Query("SELECT * FROM publisher")
    Maybe<List<RoomPublisher>> getAllPublishers();

    @Query("SELECT * FROM publisher WHERE id=:publisherId LIMIT 1")
    RoomPublisher getPublisherById(long publisherId);

    @Query("SELECT * FROM publisher WHERE name=:publisherName LIMIT 1")
    RoomPublisher getPublisherByName(String publisherName);

    @Insert
    long insertPublisher(RoomPublisher publisher);

    @Query("DELETE FROM publisher")
    void clearPublisherTable();
}
