package com.itis.android.lessondb.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Ruslan on 16.02.2018.
 */

//Класс для обмена книгами(по сути выдача книги)
@Entity(tableName = "vid",
        indices = {@Index(value = "id",unique = true),@Index(value = {"lender_id"}),
                @Index(value = {"debtor_id"}),@Index(value = {"book_id"})},
        foreignKeys = {
            @ForeignKey(
                entity = RoomReader.class,
                parentColumns = "id",
                childColumns = "lender_id",
                onDelete = CASCADE
            ),
            @ForeignKey(
                entity = RoomReader.class,
                parentColumns = "id",
                childColumns = "debtor_id",
                onDelete = CASCADE),
            @ForeignKey(
                    entity = RoomBook.class,
                    parentColumns = "id",
                    childColumns = "book_id",
                    onDelete = CASCADE)
        })
public class RoomVid {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "lender_id")
    private long lenderId;

    @ColumnInfo(name = "debtor_id")
    private long debtorId;

    @ColumnInfo(name = "book_id")
    private long bookId;

    private Date start_date;

    private Date end_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLenderId() {
        return lenderId;
    }

    public void setLenderId(long lenderId) {
        this.lenderId = lenderId;
    }

    public long getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(long debtorId) {
        this.debtorId = debtorId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
