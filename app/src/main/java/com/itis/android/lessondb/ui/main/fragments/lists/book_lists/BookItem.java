package com.itis.android.lessondb.ui.main.fragments.lists.book_lists;

import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomBook;

/**
 * Created by Ruslan on 18.02.2018.
 */

//класс-обертка для работы с recycler_view
public class BookItem {

    private long id;

    private String bookName;

    private String authorName;

    public BookItem(RoomBook book) {
        this.id = book.getId();
        this.bookName = book.getTitle();
        this.authorName = AppDatabase.getAppDatabase().getAuthorDao().getAuthorById(book.getAuthorId()).getName();
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
