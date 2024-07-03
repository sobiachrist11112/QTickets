package com.production.qtickets.interfaces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harsh on 7/19/2018.
 */
public class APIClientQticketsNovo {
    //this is the class where qtickets novo configuration is done
    private static final String BASE_URL = "https://www.q-tickets.com/Novo/";
    //private static final String BASE_URL = "http://wa-qt-mobile-api.azurewebsites.net/Novo/";
    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder client;
    private static HttpLoggingInterceptor loggingInterceptor;
    private static String authenticationToken = null;

    public static Retrofit getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder();
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

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void removeAuthenticationToken() {
        authenticationToken = null;
    }
}
