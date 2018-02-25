package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.RoomPublishingHouse;

import java.util.List;

/**
 * Created by Admin on 23.02.2018.
 */

@Dao
public interface PublishingHouseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPublishingHouse(RoomPublishingHouse roomPublishingHouse);

    @Query("SELECT * FROM publishing_house")
    List<RoomPublishingHouse> getAllPublishingHouses();

    @Query("SELECT * FROM publishing_house WHERE name= :name LIMIT 1")
    RoomPublishingHouse getPublishingHouseByName(String name);

    @Query("SELECT * FROM publishing_house WHERE id= :id LIMIT 1")
    RoomPublishingHouse getPublishingHouseById(Long id);
}
