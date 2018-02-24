package com.itis.android.lessondb.realm.repository;

import com.itis.android.lessondb.realm.entity.RealmReader;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ruslan on 16.02.2018.
 */

public interface ReaderRepository {

    Observable<List<RealmReader>> getAllReaders();

    void insertReader(RealmReader reader);

    RealmReader getReaderById(long id);

    RealmReader getReaderByNameAndPassword(String username, String password);

    RealmReader getReaderByName(String username);

    RealmReader findExistReader(String username);

    void clearDB();
}
