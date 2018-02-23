package com.itis.android.lessondb;

import android.app.Application;
import android.content.Context;

import com.itis.android.lessondb.realm.RepositoryProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;

/**
 * Created by Nail Shaykhraziev on 11.02.2018.
 */

public class App extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        App.sContext = getApplicationContext();
        setupRealm();
        RepositoryProvider.init();
    }

    public static Context getContext() {
        return sContext;
    }

    private void setupRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .rxFactory(new RealmObservableFactory())
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
