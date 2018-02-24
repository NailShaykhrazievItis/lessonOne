package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomLibrary;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by valera071998@gamil.com on 24.02.2018.
 */
@Dao
public interface LibraryDao {
    @Query("SELECT * FROM library")
    Maybe<List<RoomLibrary>> getAll();

    @Query("SELECT * FROM library where id = :libraryId LIMIT 1")
    RoomLibrary getById(long libraryId);

    @Query("SELECT * FROM author WHERE name = :libraryName LIMIT 1")
    RoomLibrary getLibraryByName(String libraryName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RoomLibrary library);

    @Update
    void update(RoomLibrary library);

    @Delete
    void delete(RoomLibrary library);

    @Query("DELETE FROM library")
    void clearTable();
}
