package com.andreibacalu.android.itodo.utils.realm;

import android.support.v7.widget.RecyclerView;

import io.realm.RealmObject;

public abstract class RealmRecyclerViewAdapter<T extends RealmObject> extends RecyclerView.Adapter {
    private RealmModelAdapter<T> realmModelAdapter;

    public RealmModelAdapter<T> getRealmModelAdapter() {
        return realmModelAdapter;
    }

    public void setRealmModelAdapter(RealmModelAdapter<T> realmModelAdapter) {
        this.realmModelAdapter = realmModelAdapter;
    }

    public T getItem(int position) {
        return realmModelAdapter.getItem(position);
    }

    @Override
    public int getItemCount() {
        if (realmModelAdapter != null) {
            return realmModelAdapter.getCount();
        }
        return 0;
    }
}
