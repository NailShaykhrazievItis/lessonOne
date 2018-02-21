package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.RoomReader;

import java.util.List;

/**
 * Created by Ruslan on 16.02.2018.
 */

@Dao
public interface ReaderDao {

    @Query("SELECT * FROM reader WHERE id = :readerId LIMIT 1")
    RoomReader getReaderById(long readerId);

    @Query("SELECT * FROM reader WHERE username = :username AND password = :password")
    RoomReader getReaderByNameAndPassword(String username, String password);

    @Insert
    long insertReader(RoomReader reader);

    @Query("SELECT * FROM reader WHERE username = :username")
    List<RoomReader> findExistReader(String username);

    /*@Update
    public void updateUsers(RoomReader... readers);

    @Delete
    void deleteReaders(RoomReader... readers);*/ // не используются в приложении, просто для полного CRUD

    @Query("DELETE FROM reader")
    void clearReaderTable();
}
