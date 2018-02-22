package com.itis.android.lessondb.realm.repository;

import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmPublisher;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by a9 on 20.02.18.
 */

public interface PublisherRepository {

    Observable<List<RealmPublisher>> getAllPublishers();

    void insertPublisher(RealmPublisher realmPublisher);

    RealmPublisher getPublisherById(long id);

    RealmPublisher getPublisherByName(String name);

}
