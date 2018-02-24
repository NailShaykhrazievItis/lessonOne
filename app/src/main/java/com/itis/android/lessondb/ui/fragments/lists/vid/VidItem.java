package com.itis.android.lessondb.ui.fragments.lists.vid;

import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomVid;

/**
 * Created by Ruslan on 18.02.2018.
 */

//класс-обертка для работы с recycler_view
public class VidItem{

    private long id;

    private String readerName;

    private String bookName;

    public VidItem(RoomVid vid, boolean isLender) {

        this.id = vid.getId();
        AppDatabase database = AppDatabase.getAppDatabase();

        if(isLender){
            this.readerName = database.getReaderDao().getReaderById(vid.getLenderId()).getUsername();
        } else {
            this.readerName = database.getReaderDao().getReaderById(vid.getDebtorId()).getUsername();
        }
        this.bookName = database.getBookDao().getBookById(vid.getBookId()).getTitle();


    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
