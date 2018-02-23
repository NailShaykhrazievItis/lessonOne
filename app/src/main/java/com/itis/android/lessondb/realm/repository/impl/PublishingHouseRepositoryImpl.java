package com.itis.android.lessondb.realm.repository.impl;

import com.itis.android.lessondb.realm.entity.RealmPublishingHouse;
import com.itis.android.lessondb.realm.repository.PublishingHouseRepository;
import com.itis.android.lessondb.realm.repository.base.BaseRepository;

import java.util.List;

/**
 * Created by Admin on 23.02.2018.
 */

public class PublishingHouseRepositoryImpl extends BaseRepository implements PublishingHouseRepository {
    @Override
    public void insertPublishingHouse(RealmPublishingHouse publishingHouse) {
        executeTransaction(realm -> {
            long id = nextKey(realm, RealmPublishingHouse.class);
            publishingHouse.setId(id);
            realm.insertOrUpdate(publishingHouse);
        });
    }

    @Override
    public List<RealmPublishingHouse> getAllPublishingHouses() {
        return getRealm().where(RealmPublishingHouse.class).findAll();
    }

    @Override
    public RealmPublishingHouse getPublishingHouseByName(String name) {
        return getRealm().where(RealmPublishingHouse.class).equalTo("name",name).findFirst();
    }

    @Override
    public RealmPublishingHouse getPublishingHouseById(Long id) {
        return getRealm().where(RealmPublishingHouse.class).equalTo("id",id).findFirst();
    }
}
