package com.itis.android.lessondb.realm.repository;

import com.itis.android.lessondb.realm.entity.Genre;
import com.itis.android.lessondb.realm.entity.RealmBook;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public interface BookRepository {

    Observable<List<RealmBook>> getAllBooks();

    void insertBook(RealmBook book);

    RealmBook getBookById(long id);

    void clearDB();

    Observable<List<RealmBook>> getBooksByGenre(Genre genre);
}
