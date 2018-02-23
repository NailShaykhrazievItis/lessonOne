package com.itis.android.lessondb.realm;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.itis.android.lessondb.realm.repository.AuthorRepository;
import com.itis.android.lessondb.realm.repository.BookRepository;
import com.itis.android.lessondb.realm.repository.ReaderRepository;
import com.itis.android.lessondb.realm.repository.impl.AuthorRepositoryImpl;
import com.itis.android.lessondb.realm.repository.impl.BookRepositoryImpl;
import com.itis.android.lessondb.realm.repository.impl.ReaderRepositoryImpl;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public final class RepositryProvider {

    private static BookRepository bookRepository;

    private static AuthorRepository authorRepository;

    private static ReaderRepository readerRepository;

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

    public static ReaderRepository provideReaderReposirory(){
        if (readerRepository == null){
            readerRepository = new ReaderRepositoryImpl();
        }
        return readerRepository;
    }

    @MainThread
    public static void init() {
        bookRepository = new BookRepositoryImpl();
    }
}
