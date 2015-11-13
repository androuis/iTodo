package com.andreibacalu.android.itodo.endpoints;

import com.example.abacalu.itodo.backend.messaging.Messaging;
import com.example.abacalu.itodo.backend.registration.Registration;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by abacalu on 13-11-15.
 */
public class CreateAppEngineEndpoints {

    public static final String SERVER_ADDRESS = "172.16.116.53";
    public static final String SERVER_PORT = "8080";
    public static final String SERVER_URL = "http://" + SERVER_ADDRESS + ":" + SERVER_PORT + "/_ah/api";

    public static Registration createRegistrationEndpoint() {
        return new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl(SERVER_URL)
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                }).build();
    }

    public static Messaging createMessagingEndpoint() {
        return new Messaging.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl(SERVER_URL)
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                }).build();
    }
}
