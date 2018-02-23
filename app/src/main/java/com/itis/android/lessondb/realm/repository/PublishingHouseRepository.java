package com.itis.android.lessondb.realm.repository;

import com.itis.android.lessondb.realm.entity.RealmPublishingHouse;

import java.util.List;

/**
 * Created by Admin on 23.02.2018.
 */

public interface PublishingHouseRepository {

    void insertPublishingHouse(RealmPublishingHouse publishingHouse);

    List<RealmPublishingHouse> getAllPublishingHouses();

    RealmPublishingHouse getPublishingHouseByName(String name);

    RealmPublishingHouse getPublishingHouseById(Long id);

}
