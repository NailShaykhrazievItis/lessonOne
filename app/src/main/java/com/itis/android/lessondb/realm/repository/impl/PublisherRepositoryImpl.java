package com.itis.android.lessondb.realm.repository.impl;

import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmPublisher;
import com.itis.android.lessondb.realm.repository.PublisherRepository;
import com.itis.android.lessondb.realm.repository.base.BaseRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by a9 on 20.02.18.
 */

public class PublisherRepositoryImpl extends BaseRepository implements PublisherRepository {
    @Override
    public Observable<List<RealmPublisher>> getAllPublishers() {
        return Observable.just(getRealm().where(RealmPublisher.class).findAll());

    }

    @Override
    public void insertPublisher(RealmPublisher realmPublisher) {
        executeTransaction(realm -> {
            long id = nextKey(realm, RealmPublisher.class); // auto-increment in realm
            realmPublisher.setId(id);
            realm.insertOrUpdate(realmPublisher);
        });
    }

    @Override
    public RealmPublisher getPublisherById(long id) {
        return getRealm().where(RealmPublisher.class).equalTo("id", id).findFirst();
    }

    @Override
    public RealmPublisher getPublisherByName(String name) {
        return getRealm().where(RealmPublisher.class).equalTo("name", name).findFirst();
    }
}
