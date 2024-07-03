package com.production.qtickets.utils;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import android.content.res.Configuration;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
//import com.google.android.gms.analytics.GoogleAnalytics;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.StandardExceptionParser;
//import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.production.qtickets.R;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MyApplication extends Application {
    public static final String TAG = MyApplication.class
            .getSimpleName();

    private static MyApplication mInstance;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        FacebookSdk.setClientToken(this.getString(R.string.fb_client_token));

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        //for language-changes coming back from webview

//      Toast.makeText(getApplicationContext(),"MyAppp ",Toast.LENGTH_SHORT).show();
        FirebaseApp.initializeApp(this);

        try {
            new WebView(this).destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setDefaultLanguage(this, "en");

        // set in-app defaults firebase updates
        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        Map<String, Object> remoteConfigDefaults = new HashMap();
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, false);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, "1.0");
        remoteConfigDefaults.put(ForceUpdateChecker.FLEXI_POPUP_DETAILS, "");
        remoteConfigDefaults.put(ForceUpdateChecker.SHOW_FLEXI_ANDROID, false);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL, "https://play.google.com/store/apps/details?id=com.mobile.android.qticketsapp");

        firebaseRemoteConfig.setDefaultsAsync(remoteConfigDefaults);

        firebaseRemoteConfig.fetch(60)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("22dec: ", "Remote config is fetched.");
                            firebaseRemoteConfig.fetchAndActivate();
                            setupForceUpdatforHuweaei();
                        } else {
                            Log.d("22dec: ", "Fetch failed.");

                            // Use default values after 30 seconds if fetch fails.
                        }
                    }
                });

    }

    private void setupForceUpdatforHuweaei() {
        // set in-app defaults firebase updates
        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        Map<String, Object> remoteConfigDefaults = new HashMap();
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED_HUAWEI, false);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION_HUAWEI, "1.0");
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL_HUAWEI,
                "https://play.google.com/store/apps/details?id=com.mobile.android.qticketsapp");
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_SOFT_UPDATE_URL_HUAWEI,
                false);

        firebaseRemoteConfig.setDefaultsAsync(remoteConfigDefaults);
        firebaseRemoteConfig.fetch(60) // fetch every minutes
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("19December", "remote config is fetched.");
                            firebaseRemoteConfig.fetchAndActivate();
                        }
                    }
                });

    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setDefaultLanguage(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }
}
