package com.andreibacalu.android.itodo.gcm;

import com.andreibacalu.android.itodo.TodoApplication;
import com.example.abacalu.itodo.backend.registration.Registration;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by abacalu on 09-11-15.
 */
public class GcmRegistrationThread extends Thread {

    public static final String SENDER_ID = "408292636937";
    private static Registration registration = null;
    private static GoogleCloudMessaging gcm;

    @Override
    public void run() {
        if (registration == null) {
            Registration.Builder builder = new Registration.Builder(
                    AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("http://172.16.100.18:8080/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            registration = builder.build();
        }
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(TodoApplication.getInstance());
            }
            String registrationId = gcm.register(SENDER_ID);
            registration.register(registrationId).execute();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
