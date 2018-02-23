package com.itis.android.lessondb.realm.repository;

import com.itis.android.lessondb.realm.entity.RealmReader;

import java.util.List;

import io.reactivex.Observable;

public interface ReaderRepository {

    List<RealmReader> getAllReaders();

    void insertReader(RealmReader reader);

}
