package com.itis.android.lessondb.realm;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.itis.android.lessondb.realm.repository.AuthorRepository;
import com.itis.android.lessondb.realm.repository.BookRepository;
import com.itis.android.lessondb.realm.repository.PublishingRepository;
import com.itis.android.lessondb.realm.repository.impl.AuthorRepositoryImpl;
import com.itis.android.lessondb.realm.repository.impl.BookRepositoryImpl;
import com.itis.android.lessondb.realm.repository.impl.PublishingRepositoryImpl;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public final class RepositoryProvider {

    private static BookRepository bookRepository;

    private static AuthorRepository authorRepository;

    private static PublishingRepository publishingRepository;

    @NonNull
    public static BookRepository provideBookRepository() {
        if (bookRepository == null) {
            bookRepository = new BookRepositoryImpl();
        }
        return bookRepository;
    }

    @NonNull
    public static AuthorRepository provideAuthorRepository() {
        if (authorRepository == null) {
            authorRepository = new AuthorRepositoryImpl();
        }
        return authorRepository;
    }

    @NonNull
    public static PublishingRepository providePublishingRepository() {
        if (publishingRepository == null) {
            publishingRepository = new PublishingRepositoryImpl();
        }
        return publishingRepository;
    }

    @MainThread
    public static void init() {
        bookRepository = new BookRepositoryImpl();
    }
}
