package com.relurori.hanblocks;

import java.io.IOException;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;

public class SpreadsheetsService {

    HttpRequestFactory wiseRequestFactory;
    HttpRequestFactory writelyRequestFactory;
    private final String applicationName;
    private final TokenSupplier tokenSupplier;
    private String wiseToken;
    private String writelyToken;
    
    private void createRequestFactories() {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();

        wiseRequestFactory = transport.createRequestFactory(new HttpRequestInitializer() {
            public void initialize(HttpRequest req) throws IOException {

                if (wiseToken == null)
                    wiseToken = tokenSupplier.getToken("wise");

                GoogleHeaders headers = new GoogleHeaders();
                headers.gdataVersion = "3";
                headers.setApplicationName(applicationName);
                headers.setGoogleLogin(wiseToken);

                req.headers = headers;
                req.enableGZipContent = true;
            }
        });
        
        writelyRequestFactory = transport.createRequestFactory(new HttpRequestInitializer() {
           public void initialize(HttpRequest req) throws IOException {
                if (writelyToken == null)
                    writelyToken = tokenSupplier.getToken("writely");


                GoogleHeaders headers = new GoogleHeaders();
                headers.gdataVersion = "3";
                headers.setApplicationName(applicationName);
                headers.setGoogleLogin(writelyToken);

                req.headers = headers;
                req.enableGZipContent = true;
            }
        });
    }
    
    public interface TokenSupplier {
        public String getToken(String authTokenType);
        public void invalidateToken(String token);
    }
    
    
}
