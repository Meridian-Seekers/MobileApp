package com.example.finalproject.services;

import static android.content.ContentValues.TAG;

import android.util.Log;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // Log the URL, headers, and body (if available)
        Log.d(TAG, "Request URL: " + request.url());
        Log.d(TAG, "Request Method: " + request.method());
        Log.d(TAG, "Request Headers: " + request.headers());

        if (request.body() != null) {
            // Log the request body (if needed, may need to buffer the body)
            Log.d(TAG, "Request Body: " + request.body().toString());
        }

        // Proceed with the request
        return chain.proceed(request);
    }
}

