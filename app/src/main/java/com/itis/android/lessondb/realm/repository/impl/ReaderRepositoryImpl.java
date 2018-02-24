package com.itis.android.lessondb.realm.repository.impl;

import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.realm.repository.ReaderRepository;
import com.itis.android.lessondb.realm.repository.base.BaseRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ruslan on 16.02.2018.
 */

public class ReaderRepositoryImpl extends BaseRepository implements ReaderRepository {
    @Override
    public Observable<List<RealmReader>> getAllReaders() {
        return Observable.just(getRealm().where(RealmReader.class).findAll());
    }

    @Override
    public void insertReader(RealmReader reader) throws IllegalArgumentException{
        RealmReader realmReader = getReaderByName(reader.getUsername());
        if (realmReader != null) {
            throw new IllegalArgumentException("Имя пользователя должно быть уникальным");
        }
        executeTransaction(realm -> {
            long id = nextKey(realm, RealmReader.class);
            reader.setId(id);
            realm.insertOrUpdate(reader);
        });
    }

    @Override
    public RealmReader getReaderById(long id) {
        return getRealm().where(RealmReader.class).equalTo("id", id).findFirst();
    }

    @Override
    public RealmReader getReaderByNameAndPassword(String username, String password) {
        return getRealm().where(RealmReader.class)
                .equalTo("username", username)
                .equalTo("password",password)
                .findFirst();
    }

    @Override
    public RealmReader getReaderByName(String username) {
        return getRealm().where(RealmReader.class)
                .equalTo("username", username)
                .findFirst();
    }

    @Override
    public RealmReader findExistReader(String readerName){
        RealmReader realmReader = getRealm()
                                    .where(RealmReader.class)
                                    .equalTo("username",readerName)
                                    .findFirst();

        return realmReader;
    }

    @Override
    public void clearDB() {
        super.clearDB();
    }
}
