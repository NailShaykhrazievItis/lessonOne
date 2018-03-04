package com.itis.android.lessondb.realm.repository.impl;

import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.realm.repository.ReaderRepository;
import com.itis.android.lessondb.realm.repository.base.BaseRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Tony on 3/2/2018.
 */

public class ReaderRepositoryImpl extends BaseRepository implements ReaderRepository {

    @Override
    public Observable<List<RealmReader>> getAllReaders() {
        return Observable.just(getRealm().where(RealmReader.class).findAll());
    }

    @Override
    public void insertReader(RealmReader realmReader) {
        executeTransaction(realm -> {
            long id = nextKey(realm, RealmReader.class); // auto-increment in realm
            realmReader.setId(id);
            realm.insertOrUpdate(realmReader);
        });
    }

    @Override
    public RealmReader getReaderById(long id) {
        return getRealm().where(RealmReader.class).equalTo("id", id).findFirst();
    }
}
