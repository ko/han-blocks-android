package com.relurori.hanblocks;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xmlpull.v1.XmlPullParserException;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
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

                HttpHeaders headers = new HttpHeaders();
                headers.setUserAgent(applicationName);
                headers.set("Accept-Encoding", "gzip");
                req.setHeaders(headers);
            }
        });
        
        writelyRequestFactory = transport.createRequestFactory(new HttpRequestInitializer() {
           public void initialize(HttpRequest req) throws IOException {
                if (writelyToken == null)
                    writelyToken = tokenSupplier.getToken("writely");


                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept-Encoding", "gzip");
                headers.setUserAgent(applicationName);

                req.setHeaders(headers);
            }
        });
    }
    
    public interface TokenSupplier {
        public String getToken(String authTokenType);
        public void invalidateToken(String token);
    }
    
    public SpreadsheetsService(String applicationName, TokenSupplier tokenSupplier) {

        this.applicationName = applicationName;
        this.tokenSupplier = tokenSupplier;

        createRequestFactories();

        // from Google IO 2011 talk:
        // Note. enabling this causes OutOfMemoryError on large spreadsheets
        Logger.getLogger("com.google.api.client.http").setLevel(Level.ALL);
        // ALSO RUN FROM SHELL: adb shell setprop log.tag.HttpTransport DEBUG

    }
    

    public static class SpreadsheetsException extends Exception {
        private static final long serialVersionUID = 7081303654609337713L;

        SpreadsheetsException(String message) {
            super(message);
        }
    }
    

    public static class SpreadsheetsHttpException extends SpreadsheetsException {
        private static final long serialVersionUID = -7988015274337294180L;
        private String statusMessage;
        private int statusCode;

        SpreadsheetsHttpException(String message) {
            super(message);
        }

        SpreadsheetsHttpException(int statusCode, String statusMessage) {
            this(Integer.toString(statusCode) + " " + statusMessage);
            this.statusCode = statusCode;
            this.statusMessage = statusMessage;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getStatusMessage() {
            return statusMessage;
        }

    }
    

    // Automatically retries all requests after invalidating tokens, and converts exceptions.
    abstract class Request<T> {
        
        abstract T run() throws IOException, XmlPullParserException;
        
        public final T execute() throws IOException, SpreadsheetsException {
            return internalExecute(true);
        }
        private final T internalExecute(boolean firstTry) throws IOException, SpreadsheetsException {
           
            try {
                T response = run();
                return response;
            } catch (HttpResponseException e) {
                if (firstTry && e.getMessage().equals("401 Token expired")) {
                    tokenSupplier.invalidateToken(wiseToken);
                    tokenSupplier.invalidateToken(writelyToken);
                    wiseToken = null;
                    writelyToken = null;

                    return internalExecute(false);
                } else {
                    throw new SpreadsheetsHttpException(e.response.statusCode, e.response.statusMessage);
                }
            } catch (XmlPullParserException e) {
                throw new SpreadsheetsException(e.getMessage());
            }
           
            
        }
    }
}
