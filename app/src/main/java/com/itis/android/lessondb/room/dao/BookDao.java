package com.itis.android.lessondb.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.itis.android.lessondb.room.entity.Genre;
import com.itis.android.lessondb.room.entity.RoomBook;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */
@Dao
public interface BookDao {

    @Query("SELECT * FROM book")
    Maybe<List<RoomBook>> getAllBooks();

    @Query("SELECT * FROM book where id = :bookId LIMIT 1")
    RoomBook getBookById(long bookId);

    @Query("SELECT * FROM book where title = :name AND genre =:genre " +
            "AND EXISTS(SELECT * FROM author WHERE author.id = author_id AND author.name =:authorName)")
    List<RoomBook> isRoomBookExist(String name, String authorName, String genre);

    @Query("SELECT * FROM book where releaseDate < :year")
    List<RoomBook> findBooksBeforeYear(long year); //filter action

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(RoomBook roomBook);

    @Query("SELECT * from book where id = :bookId LIMIT 1")
    Single<RoomBook> loadBookById(long bookId); // room supports Single, Maybe, Flowable

    @Query("UPDATE book SET genre = :genre WHERE id = :bookId") //update example
    void updateGenreById(long bookId, Genre genre);

    @Query("DELETE FROM book")
    void clearBookTable();
}
