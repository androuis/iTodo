package com.andreibacalu.android.itodo.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Iterator;
import java.util.Set;

public class GcmReceiver extends WakefulBroadcastReceiver {
    private static final String TAG_LOG = GcmReceiver.class.getSimpleName();

    public GcmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.e("onReceive", intent.toString());
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            Log.e(TAG_LOG,"Dumping Intent start");
            while (it.hasNext()) {
                String key = it.next();
                Log.e(TAG_LOG,"[" + key + "=" + bundle.get(key)+"]");
            }
            Log.e(TAG_LOG,"Dumping Intent end");
        }
    }
}
