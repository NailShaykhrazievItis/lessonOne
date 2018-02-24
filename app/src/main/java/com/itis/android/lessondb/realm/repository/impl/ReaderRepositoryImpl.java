package com.itis.android.lessondb.realm.repository.impl;

import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.realm.repository.base.BaseRepository;
import com.itis.android.lessondb.realm.repository.ReaderRepository;

import java.util.List;

import io.reactivex.Observable;


public class ReaderRepositoryImpl extends BaseRepository implements ReaderRepository {
    @Override
    public List<RealmReader> getAllReaders() {
        return getRealm().where(RealmReader.class).findAll();
    }

    @Override
    public void insertReader(RealmReader reader) {
        executeTransaction(realm -> {
            long id = nextKey(realm, RealmReader.class);
            reader.setId(id);
            realm.insertOrUpdate(reader);
        });
    }
}
