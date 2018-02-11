package com.itis.android.lessondb;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void setupRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .rxFactory(new RealmObservableFactory())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
