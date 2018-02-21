package com.itis.android.lessondb.realm.entity;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ruslan on 16.02.2018.
 */

public class RealmVid extends RealmObject {

    @PrimaryKey
    private long id;

    private RealmReader lender;

    private RealmReader debtor;

    private RealmBook book;

    private Date start_date;

    private Date end_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealmReader getLender() {
        return lender;
    }

    public void setLender(RealmReader lender) {
        this.lender = lender;
    }

    public RealmReader getDebtor() {
        return debtor;
    }

    public void setDebtor(RealmReader debtor) {
        this.debtor = debtor;
    }

    public RealmBook getBook() {
        return book;
    }

    public void setBook(RealmBook book) {
        this.book = book;
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
