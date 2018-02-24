package com.itis.android.lessondb.realm.repository;

import com.itis.android.lessondb.realm.entity.RealmAuthor;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Nail Shaykhraziev on 13.02.2018.
 */

public interface AuthorRepository {

    Observable<List<RealmAuthor>> getAllAuthors();

    void insertAuthor(RealmAuthor realmAuthor);

    RealmAuthor getAuthorById(long id);

    RealmAuthor getAuthorByName(String name);

}
