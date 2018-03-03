package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomReader;

/**
 * Created by Tony on 3/3/2018.
 */
@Dao
public interface ReaderDao {

    @Query("SELECT * FROM reader WHERE id = :readerId LIMIT 1")
    RoomReader getReaderById(long readerId);

    @Insert
    long insertReader(RoomReader reader); //room can return id of entity

    @Query("DELETE FROM reader")
    void clearReaderTable();

    @Query("SELECT * FROM reader WHERE name = :name LIMIT 1")
    RoomReader getReaderByName(String name);

}
