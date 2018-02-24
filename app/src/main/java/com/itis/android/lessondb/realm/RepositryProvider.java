package com.itis.android.lessondb.realm;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.itis.android.lessondb.realm.repository.AuthorRepository;
import com.itis.android.lessondb.realm.repository.BookRepository;
import com.itis.android.lessondb.realm.repository.ReaderRepository;
import com.itis.android.lessondb.realm.repository.VidRepository;
import com.itis.android.lessondb.realm.repository.impl.AuthorRepositoryImpl;
import com.itis.android.lessondb.realm.repository.impl.BookRepositoryImpl;
import com.itis.android.lessondb.realm.repository.impl.ReaderRepositoryImpl;
import com.itis.android.lessondb.realm.repository.impl.VidRepositoryImpl;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public final class RepositryProvider {

    private static BookRepository bookRepository;

    private static AuthorRepository authorRepository;

    private static ReaderRepository readerRepository;

    private static VidRepository vidRepository;

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
    public static ReaderRepository provideReaderRepository() {
        if (readerRepository == null) {
            readerRepository = new ReaderRepositoryImpl();
        }
        return readerRepository;
    }


    @NonNull
    public static VidRepository provideVidRepository() {
        if (vidRepository == null) {
            vidRepository = new VidRepositoryImpl();
        }
        return vidRepository;
    }


    @MainThread
    public static void init() {
        bookRepository = new BookRepositoryImpl();
    }
}
