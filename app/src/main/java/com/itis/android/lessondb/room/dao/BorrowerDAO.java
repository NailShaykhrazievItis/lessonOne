package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.RoomBorrower;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by Blaheart on 24.02.2018.
 */

@Dao
public interface BorrowerDAO {

    @Query("SELECT * FROM borrower")
    Maybe<List<RoomBorrower>> getAllBorrowers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBorrower(RoomBorrower borrover);

    @Query("DELETE FROM borrower")
    void clearBorrowers();
}
