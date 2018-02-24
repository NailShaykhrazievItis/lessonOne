package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.RoomVid;

import java.util.List;

/**
 * Created by Ruslan on 16.02.2018.
 */

@Dao
public interface VidDao {

    @Query("SELECT * FROM vid WHERE id = :vidId LIMIT 1")
    RoomVid getVidById(long vidId);

    @Query("SELECT * FROM vid WHERE lender_id = :lenderId AND start_date !=:startDate")
    List<RoomVid> getVidByLender(long lenderId, long startDate);

    @Query("SELECT * FROM vid WHERE debtor_id = :debtorId")
    List<RoomVid> getVidByDebtor(long debtorId);

    @Query("SELECT * FROM vid WHERE lender_id = :readerId AND start_date =:startDate")
    List<RoomVid> getVidByReader(long readerId, long startDate);

    @Query("SELECT * FROM vid WHERE book_id =:bookId LIMIT 6")
    List<RoomVid> getVidByBookWithLimit(long bookId);

    @Query("SELECT * FROM vid WHERE book_id =:bookId")
    List<RoomVid> getVidByBook(long bookId);

    @Insert()
    long insertVid(RoomVid vid);

    @Query("UPDATE vid SET debtor_id = :debtorId ,start_date =:startDate ,end_date =:endDate WHERE id =:vidId ")
    void updateVid(long vidId, long debtorId, long startDate, long endDate);

    /*@Query("UPDATE vid SET debtor_id =:debtorId , start_date =:startDate ,end_date =:endDate WHERE id =:vidId ")
    void deleteDebtorFromVid(long debtorId,long startDate,long endDate,long vidId);*/

    /*@Update
    public void updateUsers(RoomVid... roomVids);

    @Delete
    void deleteReaders(RoomVid... roomVids);*/ // не используются в приложении, просто для полного CRUD

    @Query("DELETE FROM vid")
    void clearReaderTable();
}
