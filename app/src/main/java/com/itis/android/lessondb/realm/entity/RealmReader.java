package com.itis.android.lessondb.realm.entity;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmReader extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private int age;
    private RealmBook book;

    public RealmBook getBook() {
        return book;
    }

    public void setBook(RealmBook book) {
        this.book = book;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
