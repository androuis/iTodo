package com.andreibacalu.android.itodo.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.andreibacalu.android.itodo.R;
import com.andreibacalu.android.itodo.gcm.GcmRegistrationThread;
import com.andreibacalu.android.itodo.gcm.RegistrationIntentService;
import com.andreibacalu.android.itodo.list.AddTaskFragment;
import com.example.abacalu.itodo.backend.messaging.Messaging;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class TodosActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG_LOG = TodosActivity.class.getSimpleName();
    private GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                getFragmentManager().beginTransaction().add(R.id.content, AddTaskFragment.newInstance()).commit();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_register_new) {
            registerUsingInstanceId();
        } else if (id == R.id.action_register_old) {
            registerUsingGcm();
        } else if (id == R.id.action_send_mesage) {
            sendMessage();
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerUsingInstanceId() {
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    private void registerUsingGcm() {
        new GcmRegistrationThread().start();
    }

    private void sendMessage() {
        new Thread(){
            @Override
            public void run() {
                sendUsingEndpoint();
                sendUsingGcm();
            }
        }.start();
    }

    private void sendUsingEndpoint() {
        Messaging messaging = new Messaging.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("http://172.16.100.18:8080/_ah/api")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                }).build();
        try {
            messaging.messagingEndpoint().sendMessage("test " + System.currentTimeMillis()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendUsingGcm() {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
        Bundle data = new Bundle();
        data.putString("message", "test");
        data.putString("action", "com.antoinecampbell.gcmdemo.ECHO");
        try {
            gcm.send("408292636937@gcm.googleapis.com", String.valueOf(System.currentTimeMillis()), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG_LOG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
