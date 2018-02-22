package com.itis.android.lessondb.realm.entity;



import com.itis.android.lessondb.general.Book;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by Nail Shaykhraziev on 12.02.2018.
 */

public class RealmBook extends RealmObject implements Book {

    @PrimaryKey
    private long id;
    private String title;
    private String desc;
    private Date releaseDate;
    private RealmAuthor realmAuthor;
    private RealmPublisher realmPublisher;

    private String genreRealm;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public RealmAuthor getRealmAuthor() {
        return realmAuthor;
    }

    public void setRealmAuthor(RealmAuthor realmAuthor) {
        this.realmAuthor = realmAuthor;
    }

    public RealmPublisher getRealmPublisher() {
        return realmPublisher;
    }

    public void setRealmPublisher(RealmPublisher realmPublisher) {
        this.realmPublisher = realmPublisher;
    }


    public GenreRealm getGenreRealm() {
        return (genreRealm != null) ? GenreRealm.valueOf(genreRealm) : null;
    }

    public void setGenreRealm(GenreRealm genreRealm) {
        this.genreRealm = genreRealm.toString();
    }


    @Override
    public String toString() {
        return "RealmBook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", releaseDate=" + releaseDate +
                ", realmAuthor=" + realmAuthor +
                ", realmPublisher=" + realmPublisher +
                ", genreRealm='" + genreRealm + '\'' +
                '}';
    }
}
