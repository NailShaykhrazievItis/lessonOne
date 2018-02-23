package com.itis.android.lessondb.realm.repository;

import com.itis.android.lessondb.realm.entity.RealmPublishing;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by User on 18.02.2018.
 */

public interface PublishingRepository {

    Observable<List<RealmPublishing>> getAllPublishings();

    void insertPublishing(RealmPublishing realmPublishing);

    RealmPublishing getPublishById(long id);

}
