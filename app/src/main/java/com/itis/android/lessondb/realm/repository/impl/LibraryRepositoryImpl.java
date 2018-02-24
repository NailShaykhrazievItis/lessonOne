package com.itis.android.lessondb.realm.repository.impl;

import com.itis.android.lessondb.realm.entity.RealmLibrary;
import com.itis.android.lessondb.realm.repository.base.BaseRepository;
import com.itis.android.lessondb.realm.repository.LibraryRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by valera071998@gamil.com on 19.02.2018.
 */

public class LibraryRepositoryImpl extends BaseRepository implements LibraryRepository {
    @Override
    public void insert(RealmLibrary realmLibrary) {
        executeTransaction(realm -> {
            long id = nextKey(realm, realmLibrary.getClass());
            realmLibrary.setId(id);
            realm.insertOrUpdate(realmLibrary);
        });
    }

    @Override
    public Observable<List<RealmLibrary>> getAllLibraries() {
        return Observable.just(getRealm().where(RealmLibrary.class).findAll());
    }

    @Override
    public RealmLibrary getLibraryById(long id) {
        return getRealm().where(RealmLibrary.class).equalTo("id", id).findFirst();
    }

    @Override
    public RealmLibrary getLibraryByName(String name) {
        return getRealm().where(RealmLibrary.class).equalTo("name", name).findFirst();
    }

    @Override
    public void clearDB() {
        clearDB();
    }
}
