package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.RoomReader;

import java.util.List;

@Dao
public interface ReaderDao {

    @Query("SELECT * FROM reader WHERE id = :readerId LIMIT 1")
    RoomReader getReaderById(long readerId);

    @Query("SELECT * FROM reader")
    List<RoomReader> getAllReaders();

    @Insert
    long insertReader(RoomReader reader);

    @Query("DELETE FROM reader")
    void clearReaderTable();

    @Query("SELECT * FROM reader WHERE book_id = :bookId")
    List<RoomReader> getReadersByBookId(long bookId);
}
