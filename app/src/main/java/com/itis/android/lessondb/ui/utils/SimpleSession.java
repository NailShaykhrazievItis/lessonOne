package com.itis.android.lessondb.ui.utils;

import com.itis.android.lessondb.realm.entity.RealmReader;
import com.itis.android.lessondb.room.entity.RoomReader;

/**
 * Created by Ruslan on 20.02.2018.
 */

//типа простейшей сессии,ни разу не делал подобного
public class SimpleSession {

    private static RoomReader roomReader;

    private static RealmReader realmReader;

    public static RoomReader getRoomReader() {
        return roomReader;
    }

    public static void setRoomReader(RoomReader roomReader) {
        SimpleSession.roomReader = roomReader;
    }

    public static RealmReader getRealmReader() {
        return realmReader;
    }

    public static void setRealmReader(RealmReader realmReader) {
        SimpleSession.realmReader = realmReader;
    }
}
