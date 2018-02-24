package com.itis.android.lessondb.realm.repository;

import com.itis.android.lessondb.realm.entity.RealmLibrary;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by valera071998@gamil.com on 19.02.2018.
 */

public interface LibraryRepository {
    void insert(RealmLibrary realmLibrary);

    Observable<List<RealmLibrary>> getAllLibraries();
    RealmLibrary getLibraryById(long id);
    RealmLibrary getLibraryByName(String name);

    void clearDB();
}
