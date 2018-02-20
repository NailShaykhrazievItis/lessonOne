package com.itis.android.lessondb.realm;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.itis.android.lessondb.realm.repository.AuthorRepository;
import com.itis.android.lessondb.realm.repository.BookRepository;
import com.itis.android.lessondb.realm.repository.PublisherRepository;
import com.itis.android.lessondb.realm.repository.impl.AuthorRepositoryImpl;
import com.itis.android.lessondb.realm.repository.impl.BookRepositoryImpl;
import com.itis.android.lessondb.realm.repository.impl.PublisherRepositoryImpl;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public final class RepositryProvider {

    private static BookRepository bookRepository;

    private static AuthorRepository authorRepository;

    private static PublisherRepository publisherRepository;

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
    public static PublisherRepository providePublisherRepository() {
        if (publisherRepository == null) {
            publisherRepository = new PublisherRepositoryImpl();
        }
        return publisherRepository;
    }

    @MainThread
    public static void init() {
        bookRepository = new BookRepositoryImpl();
    }
}
