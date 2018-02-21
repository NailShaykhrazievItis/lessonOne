package com.itis.android.lessondb.realm.repository.impl;

import android.widget.Toast;

import com.itis.android.lessondb.App;
import com.itis.android.lessondb.realm.entity.RealmBook;
import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.realm.entity.RealmVid;
import com.itis.android.lessondb.realm.repository.BookRepository;
import com.itis.android.lessondb.realm.repository.ReaderRepository;
import com.itis.android.lessondb.realm.repository.VidRepository;
import com.itis.android.lessondb.realm.repository.base.BaseRepository;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ruslan on 16.02.2018.
 */

public class VidRepositoryImpl extends BaseRepository implements VidRepository {
    @Override
    public Observable<List<RealmVid>> getAllVids() {
        return Observable.just(getRealm().where(RealmVid.class).findAll());
    }

    @Override
    public void insertVid(RealmVid vid) {
        try {
            ReaderRepository readerRepository = new ReaderRepositoryImpl();
            BookRepository bookRepository = new BookRepositoryImpl();
            RealmReader lender = readerRepository.getReaderById(vid.getLender().getId());
            RealmReader debtor = readerRepository.getReaderById(vid.getDebtor().getId());
            RealmBook book = bookRepository.getBookById(vid.getBook().getId());
            if(lender != null && debtor != null && book != null){
                throw new IllegalArgumentException("Нельзя получить книгу, так как она уже была выдана");
            }
            executeTransaction(realm -> {
                long id = nextKey(realm, RealmReader.class);
                vid.setId(id);
                realm.insertOrUpdate(vid);
            });

        }catch (IllegalArgumentException ex){
            Toast.makeText(App.getContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public RealmVid getVidById(long vidId) {
        return getRealm().where(RealmVid.class).equalTo("id",vidId).findFirst();
    }

    @Override
    public List<RealmVid> getVidByLender(long lenderId, long date) {
        List<RealmVid> results = getRealm()
                                .where(RealmVid.class)
                                .equalTo("lender.id",lenderId)
                                .notEqualTo("startDate",date)
                                .findAll();
        return results;
    }

    @Override
    public List<RealmVid> getVidByDebtor(long debtorId) {
        List<RealmVid> results = getRealm().where(RealmVid.class).equalTo("debtor.id",debtorId).findAll();
        return results;
    }

    @Override
    public List<RealmVid> getVidByBook(long bookId) {
        List<RealmVid> results = getRealm().where(RealmVid.class).equalTo("book.id",bookId).findAll();
        return results;
    }

    @Override
    public List<RealmVid> getVidByReader(long readerId, long startDate) {
        List<RealmVid> results = getRealm()
                                .where(RealmVid.class)
                                .equalTo("lender.id",readerId)
                                .equalTo("startDate",startDate)
                                .findAll();
        return results;
    }

    @Override
    public List<RealmVid> getVidByBookWithLimit(long bookId) {
        List<RealmVid> results = getRealm()
                .where(RealmVid.class)
                .equalTo("book.id",bookId)
                .findAll();
        return results.subList(0,Math.max(results.size(),6));
    }

    @Override
    public void updateVid(long vidId, long debtorId, long startDate, long endDate) {
        RealmVid vid = getRealm().where(RealmVid.class).equalTo("id",vidId).findFirst();
        if(vid != null){
            RealmReader debtor = getRealm().where(RealmReader.class).equalTo("id",debtorId).findFirst();
            vid.setDebtor(debtor);
            vid.setStart_date(new Date(startDate));
            vid.setEnd_date(new Date(endDate));
            executeTransaction(realm -> {
                realm.insertOrUpdate(vid);
            });

        }
    }

    @Override
    public void clearDB() {
        super.clearDB();
    }
}
