package com.andreibacalu.android.itodo.gcm;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;

public class TodoGcmListenerService extends GcmListenerService {
    private static final String TAG_LOG = TodoGcmListenerService.class.getSimpleName();

    public TodoGcmListenerService() {
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG_LOG, "From: " + from);
        Log.d(TAG_LOG, "Message: " + message);

        Toast.makeText(getApplicationContext(), "From: " + from + " message: " + message, Toast.LENGTH_LONG).show();

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }
    }
}
