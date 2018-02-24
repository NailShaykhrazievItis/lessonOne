package com.itis.android.lessondb.realm.repository.impl;

import com.itis.android.lessondb.realm.entity.Genre;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.repository.BookRepository;
import com.itis.android.lessondb.realm.repository.base.BaseRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public class BookRepositoryImpl extends BaseRepository implements BookRepository {

    @Override
    public Observable<List<RealmBook>> getAllBooks() {
        return Observable.just(getRealm().where(RealmBook.class).findAll());
    }

    @Override
    public RealmBook getBookById(long id) {
        return getRealm().where(RealmBook.class).equalTo("id", id).findFirst();
    }

    @Override
    public void insertBook(RealmBook book) {
        executeTransaction(realm -> {
            long id = nextKey(realm, RealmBook.class); // auto-increment in realm
            book.setId(id);
            realm.insertOrUpdate(book);
        });
    }

    @Override
    public void clearDB() {
        super.clearDB();
    }

    @Override
    public Observable<List<RealmBook>> getBooksByGenre(Genre genre) {
        return Observable.just(getRealm().where(RealmBook.class).equalTo("genre", genre.toString()).findAll());
    }
}
