package com.andreibacalu.android.itodo;

import android.app.Application;
import android.content.Context;

/**
 * Created by abacalu on 06-11-15.
 */
public class TodoApplication extends Application {

    private static TodoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static TodoApplication getInstance() {
        return instance;
    }
}
