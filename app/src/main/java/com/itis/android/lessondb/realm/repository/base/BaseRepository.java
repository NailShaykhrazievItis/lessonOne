package com.itis.android.lessondb.realm.repository.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public abstract class BaseRepository {

    protected Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    protected long nextKey(Realm realm, final Class<? extends RealmObject> c) {
        Number maxId = realm.where(c).max("id");
        return (maxId == null) ? 1 : maxId.longValue() + 1;
    }

    protected void clearDB() {
        executeTransaction(realm -> realm.deleteAll());
    }

    protected void executeTransaction(@NonNull Realm.Transaction transaction) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(transaction);
        } catch (Throwable e) {
            Log.e("Realm", e.getMessage());
        } finally {
            close(realm);
        }
    }

    private void close(@Nullable Realm realm) {
        if (realm != null) {
            realm.close();
        }
    }
}
