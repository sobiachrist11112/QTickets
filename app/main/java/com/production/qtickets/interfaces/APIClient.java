package com.production.qtickets.interfaces;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harsh on 12/7/2017.
 */

public class APIClient {
    //this is the class where we are storing the info about the base url and the base class for the retrofit network calls
    private static final String BASE_URL_QT5 = "https://qticketsapi.mindztechnology.com";
    private static final String BASE_URL_QT5_EVENT = "https://apie.q-tickets.com/";
    //  private static final String BASE_URL_QT5_EVENT = "http://192.168.1.29:1002/";
    private static final String BASE_URL = "https://api.q-tickets.com/";
    private static final String BASE_URL_NEW = "http://buytickets.me/";
    private static final String BASE_URL_UAE = "http://uaeapi.novocinemas.com/api/";
    // private static final String BASE_URL =  "http://wa-qt-mobile-api.azurewebsites.net/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofitQT5 = null;
    private static Retrofit retrofit1 = null;
    private static Retrofit retrofitUAE = null;
    private static OkHttpClient.Builder client, client1, clientUAE;
    private static HttpLoggingInterceptor loggingInterceptor;
    private static String authenticationToken = null;

    public static Retrofit getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS);

        }
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                //authenticationToken = SharedPref.getInstance().getUserToken();

                // Request customization: add request headers
                Request.Builder requestBuilder =
                        original.newBuilder().header("Content-Type", "application/json");
                original.newBuilder().header("Accept", "application/json");
                //  requestBuilder.addHeader("User-Agent", System.getProperty( "http.agent" ));

                //if (!TextUtils.isEmpty(authenticationToken)) {
                //  requestBuilder.header("Authorization", "Bearer " + authenticationToken);
                //}
                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        Gson gson = new GsonBuilder().setLenient().create();
        // if (BuildConfig.DEBUG) {
        if (loggingInterceptor == null) {
            loggingInterceptor = new HttpLoggingInterceptor();
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        //}
        OkHttpClient clients = client.build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(clients)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }


    public static Retrofit getClientQT5() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS);

        }
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder =
                        original.newBuilder().header("Content-Type", "application/json");
                original.newBuilder().header("accept", "text/plain");
                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        Gson gson = new GsonBuilder().setLenient().create();
        if (loggingInterceptor == null) {
            loggingInterceptor = new HttpLoggingInterceptor();
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        //}
        OkHttpClient clients = client.build();
        if (retrofitQT5 == null) {
            retrofitQT5 = new Retrofit.Builder().baseUrl(BASE_URL_QT5_EVENT)
                    .client(clients)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofitQT5;
    }


    public static Retrofit getLiesureClient() {
        if (client1 == null) {
            client1 = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS);

        }
        client1.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                //authenticationToken = SharedPref.getInstance().getUserToken();

                // Request customization: add request headers
                Request.Builder requestBuilder =
                        original.newBuilder().header("Content-Type", "application/json");
                original.newBuilder().header("Accept", "application/json");
                //  requestBuilder.addHeader("User-Agent", System.getProperty( "http.agent" ));

                //if (!TextUtils.isEmpty(authenticationToken)) {
                //  requestBuilder.header("Authorization", "Bearer " + authenticationToken);
                //}
                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        Gson gson = new GsonBuilder().setLenient().create();
        // if (BuildConfig.DEBUG) {
        if (loggingInterceptor == null) {
            loggingInterceptor = new HttpLoggingInterceptor();
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client1.addInterceptor(loggingInterceptor);
        //}
        OkHttpClient clients1 = client1.build();
        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder().baseUrl(BASE_URL_NEW)
                    .client(clients1)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit1;
    }

    public static Retrofit getUAEClient() {
        if (clientUAE == null) {
            clientUAE = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS);

        }
        clientUAE.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                //authenticationToken = SharedPref.getInstance().getUserToken();

                // Request customization: add request headers
                Request.Builder requestBuilder =
                        original.newBuilder().header("Content-Type", "application/json");
                original.newBuilder().header("Accept", "application/json");
                //  requestBuilder.addHeader("User-Agent", System.getProperty( "http.agent" ));

                //if (!TextUtils.isEmpty(authenticationToken)) {
                //  requestBuilder.header("Authorization", "Bearer " + authenticationToken);
                //}
                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        Gson gson = new GsonBuilder().setLenient().create();
        // if (BuildConfig.DEBUG) {
        if (loggingInterceptor == null) {
            loggingInterceptor = new HttpLoggingInterceptor();
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client1.addInterceptor(loggingInterceptor);
        //}
        OkHttpClient clients1 = client1.build();
        if (retrofitUAE == null) {
            retrofitUAE = new Retrofit.Builder().baseUrl(BASE_URL_UAE)
                    .client(clients1)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofitUAE;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void removeAuthenticationToken() {
        authenticationToken = null;
    }
}
