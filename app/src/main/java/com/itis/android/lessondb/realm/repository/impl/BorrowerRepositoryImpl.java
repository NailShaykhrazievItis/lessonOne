package com.itis.android.lessondb.realm.repository.impl;

import com.itis.android.lessondb.realm.entity.RealmBorrower;
import com.itis.android.lessondb.realm.repository.BorrowerRepository;
import com.itis.android.lessondb.realm.repository.base.BaseRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Blaheart on 24.02.2018.
 */

public class BorrowerRepositoryImpl extends BaseRepository implements BorrowerRepository {

    @Override
    public Observable<List<RealmBorrower>> getAllBorrowers() {
        return Observable.just(getRealm().where(RealmBorrower.class).findAll());
    }

    @Override
    public void insertBorrower(RealmBorrower realmBorrower) {
        executeTransaction(realm -> {
            long id = nextKey(realm, RealmBorrower.class);
            realmBorrower.setId(id);
            realm.insertOrUpdate(realmBorrower);
        });
    }

    @Override
    public void clearDB() {
        super.clearDB();
    }
}
