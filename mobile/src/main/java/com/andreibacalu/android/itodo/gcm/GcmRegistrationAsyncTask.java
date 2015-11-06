package com.andreibacalu.android.itodo.gcm;

import android.os.AsyncTask;
import android.widget.Toast;

import com.andreibacalu.android.itodo.TodoApplication;
import com.example.abacalu.itodo.backend.registration.Registration;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by abacalu on 06-11-15.
 */
public class GcmRegistrationAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String SENDER_ID = "408292636937";

    private static Registration registration = null;
    private GoogleCloudMessaging gcm;

    @Override
    protected String doInBackground(Void... params) {
        if (registration == null) {
            Registration.Builder builder = new Registration.Builder(
                    AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("http://172.16.100.150:8080/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            registration = builder.build();
        }
        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(TodoApplication.getInstance());
            }
            String registrationId = gcm.register(SENDER_ID);
            msg = "Device registered, registrationId: " + registrationId;
            registration.register(registrationId).execute();
        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(TodoApplication.getInstance(), s, Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, s);
    }
}
