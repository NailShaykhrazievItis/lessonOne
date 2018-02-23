package com.itis.android.lessondb.realm.repository.impl;

import com.itis.android.lessondb.realm.entity.RealmAuthor;
import com.itis.android.lessondb.realm.repository.AuthorRepository;
import com.itis.android.lessondb.realm.repository.base.BaseRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Nail Shaykhraziev on 13.02.2018.
 */

public class AuthorRepositoryImpl extends BaseRepository implements AuthorRepository {

    @Override
    public Observable<List<RealmAuthor>> getAllAuthors() {
        return Observable.just(getRealm().where(RealmAuthor.class).findAll());
    }

    @Override
    public RealmAuthor getAuthorByName(String authorName) {
        return getRealm().where(RealmAuthor.class).equalTo("name", authorName).findFirst();
    }


    @Override
    public void insertAuthor(RealmAuthor realmAuthor) {
        executeTransaction(realm -> {
            long id = nextKey(realm, RealmAuthor.class); // auto-increment in realm
            realmAuthor.setId(id);
            realm.insertOrUpdate(realmAuthor);
        });
    }

    @Override
    public RealmAuthor getAuthorById(long id) {
        return getRealm().where(RealmAuthor.class).equalTo("id", id).findFirst();
    }
}
