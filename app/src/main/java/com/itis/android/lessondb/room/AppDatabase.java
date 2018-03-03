package com.itis.android.lessondb.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.itis.android.lessondb.App;
import com.itis.android.lessondb.room.converters.DateConverter;
import com.itis.android.lessondb.room.converters.GenreConverter;
import com.itis.android.lessondb.room.dao.AuthorDao;
import com.itis.android.lessondb.room.dao.BookDao;
import com.itis.android.lessondb.room.dao.ReaderDao;
import com.itis.android.lessondb.room.entity.RoomAuthor;
import com.itis.android.lessondb.room.entity.RoomBook;
import com.itis.android.lessondb.room.entity.RoomReader;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */
@Database(entities = {RoomBook.class, RoomAuthor.class, RoomReader.class}, version = 1)
@TypeConverters({DateConverter.class, GenreConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase() {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(App.getContext(), AppDatabase.class,
                    "user-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract BookDao getBookDao();

    public abstract AuthorDao getAuthorDao();

    public abstract ReaderDao getReaderDao();

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
