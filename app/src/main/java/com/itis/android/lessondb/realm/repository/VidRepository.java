package com.itis.android.lessondb.realm.repository;

import com.itis.android.lessondb.realm.entity.RealmVid;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ruslan on 16.02.2018.
 */

public interface VidRepository {

    Observable<List<RealmVid>> getAllVids();

    void insertVid(RealmVid vid);

    RealmVid getVidById(long vidId);

    List<RealmVid> getVidByLender(long lenderId, long startDate);

    List<RealmVid> getVidByDebtor(long debtorId);

    List<RealmVid> getVidByBook(long bookId);

    List<RealmVid> getVidByReader(long readerId, long startDate);

    List<RealmVid> getVidByBookWithLimit(long bookId);

    void updateVid(long vidId, long debtorId, long startDate, long endDate);

    void clearDB();
}
