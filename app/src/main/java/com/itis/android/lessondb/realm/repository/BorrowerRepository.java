package com.itis.android.lessondb.realm.repository;

import com.itis.android.lessondb.realm.entity.RealmBorrower;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Blaheart on 24.02.2018.
 */

public interface BorrowerRepository {

    Observable<List<RealmBorrower>> getAllBorrowers();

    void insertBorrower(RealmBorrower realmBorrower);

    void clearDB();
}
