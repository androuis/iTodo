package com.andreibacalu.android.itodo.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.andreibacalu.android.itodo.R;
import com.andreibacalu.android.itodo.endpoints.CreateAppEngineEndpoints;
import com.andreibacalu.android.itodo.gcm.GcmRegistrationThread;
import com.andreibacalu.android.itodo.gcm.RegistrationIntentService;
import com.andreibacalu.android.itodo.task.AddTaskFragment;
import com.andreibacalu.android.itodo.task.OnBackPressedListener;
import com.andreibacalu.android.itodo.task.ViewTaskFragment;
import com.andreibacalu.android.itodo.utils.FragmentUtils;
import com.example.abacalu.itodo.backend.messaging.Messaging;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

public class TodosActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG_LOG = TodosActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            // Do first time initialization -- add initial fragment.
            Fragment newFragment = ViewTaskFragment.newInstance();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.content, newFragment).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                getFragmentManager().beginTransaction()
                        .replace(R.id.content, AddTaskFragment.newInstance(), AddTaskFragment.BACKSTACK_NAME)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(AddTaskFragment.BACKSTACK_NAME)
                .commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = FragmentUtils.getCurrentFragment(this);
        if (fragment instanceof OnBackPressedListener) {
            ((OnBackPressedListener) fragment).onBackPressed();
        } else if (!getFragmentManager().popBackStackImmediate()) {
            finish();
        }
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
        Messaging messaging = CreateAppEngineEndpoints.createMessagingEndpoint();
        String message = "test using endpoint " + new Date().toString();
        try {
            messaging.messagingEndpoint().sendMessage(URLEncoder.encode(message)).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendUsingGcm() {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
        Bundle data = new Bundle();
        String message = "test usging gcm " + new Date().toString();
        data.putString("message", URLEncoder.encode(message));
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
