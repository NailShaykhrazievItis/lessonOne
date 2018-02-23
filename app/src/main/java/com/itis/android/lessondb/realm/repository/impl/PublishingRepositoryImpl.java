package com.itis.android.lessondb.realm.repository.impl;

import com.itis.android.lessondb.realm.entity.RealmPublishing;
import com.itis.android.lessondb.realm.repository.PublishingRepository;
import com.itis.android.lessondb.realm.repository.base.BaseRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by User on 18.02.2018.
 */

public class PublishingRepositoryImpl extends BaseRepository implements PublishingRepository {

    @Override
    public Observable<List<RealmPublishing>> getAllPublishings() {
        return Observable.just(getRealm().where(RealmPublishing.class).findAll());
    }

    @Override
    public void insertPublishing(RealmPublishing realmPublishing) {
        executeTransaction(realm -> {
            long id = nextKey(realm, RealmPublishing.class); // auto-increment in realm
            realmPublishing.setId(id);
            realm.insertOrUpdate(realmPublishing);
        });
    }

    @Override
    public RealmPublishing getPublishById(long id) {
        return getRealm().where(RealmPublishing.class).equalTo("id", id).findFirst();
    }

}
