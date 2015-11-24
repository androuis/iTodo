package com.andreibacalu.android.itodo.task.adapters;

import android.content.Context;

import com.andreibacalu.android.itodo.utils.realm.RealmModelAdapter;

import io.realm.RealmResults;

public class ViewTaskRealmAdapter extends RealmModelAdapter {
    public ViewTaskRealmAdapter(Context context, RealmResults realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
