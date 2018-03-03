package com.itis.android.lessondb.realm.repository;

import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.entity.RealmReader;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Tony on 3/2/2018.
 */

public interface ReaderRepository {

    Observable<List<RealmReader>> getAllReaders();

    void insertReader(RealmReader realmReader);

    RealmReader getReaderById(long id);

}
