package com.production.qtickets.interfaces;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import retrofit2.http.Header;

/**
 * Created by Harsh on 7/12/2018.
 */
public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken)
                 .header("Content-Type","application/json");

        Request request = builder.build();
        return chain.proceed(request);
    }
}