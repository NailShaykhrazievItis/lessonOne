package com.itis.android.lessondb.utils;

import android.util.Log;

import com.itis.android.lessondb.room.AppDatabase;
import com.itis.android.lessondb.room.entity.RoomReader;
import com.itis.android.lessondb.room.entity.RoomVid;

import java.util.Date;

/**
 * Created by Ruslan on 20.02.2018.
 */

public class VidHelper {

    /*Костыльный класс из-за недостатков Room,о которых я узнал слишком поздно.Так нельзя сделать Foreign Key nullable,
    отчего приходится городить вот это(по сути все тут есть заглушки).*/

    private static long DEBTOR_ID;

    private static final String READER_NAME = "null";

    //дата типа null
    public static Date getTypicalDate(){
        Date date = new Date();
        date.setTime(0);
        return date;
    }

    public static long getDebtorId(){
        return DEBTOR_ID;
    }

    public static void setDebtorId(long debtorId) {
        VidHelper.DEBTOR_ID = debtorId;
    }

    public static String getReaderName() {
        return READER_NAME;
    }

    public static void setAllNullParams(RoomVid roomVid){
        roomVid.setDebtorId(getDebtorId());
        roomVid.setStart_date(getTypicalDate());
        roomVid.setEnd_date(getTypicalDate());
    }

    public static void createTypicalReader(){
        if(!checkTypicalReader()){
            RoomReader reader = new RoomReader();
            reader.setUsername(READER_NAME);
            reader.setPassword(READER_NAME);
            reader.setCity(READER_NAME);
            reader.setBirthday(getTypicalDate());
            setDebtorId(AppDatabase.getAppDatabase().getReaderDao().insertReader(reader));
        }
    }

    private static boolean checkTypicalReader(){
        RoomReader reader = AppDatabase.getAppDatabase().getReaderDao().getReaderByNameAndPassword(READER_NAME,READER_NAME);
        if(reader == null){
            return false;
        } else {

            Log.d(Const.TAG_LOG,"debtorId = " + reader.getId());

            setDebtorId(reader.getId());
            return true;
        }
    }
}
