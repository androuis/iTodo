package com.andreibacalu.android.itodo;

import android.app.Application;
import android.content.Context;

import com.andreibacalu.android.itodo.gcm.GcmRegistrationThread;
import com.andreibacalu.android.itodo.user.User;
import com.example.abacalu.itodo.backend.registration.Registration;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by abacalu on 06-11-15.
 */
public class TodoApplication extends Application {

    private static TodoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(User.ANONYMOUS);
        realm.commitTransaction();
        realm.close();
        instance = this;
        //registerGcm();
    }

    private void registerGcm() {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static TodoApplication getInstance() {
        return instance;
    }
}
