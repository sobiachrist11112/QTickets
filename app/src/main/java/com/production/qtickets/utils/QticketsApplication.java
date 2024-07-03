/*
package com.production.qtickets.utils;

import android.os.Build;
import android.os.StrictMode;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;

public class QticketsApplication extends MultiDexApplication {

    private static final String TAG = QticketsApplication.class.getSimpleName();
    private static QticketsApplication qticketsApplication;

    public static QticketsApplication getInstance() {

        return qticketsApplication;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        // ACRA.init(this);
        if (Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
        }
        MultiDex.install(this);
        qticketsApplication = this;
        FirebaseApp.initializeApp(qticketsApplication);


//        // set in-app defaults
//        Map<String, Object> remoteConfigDefaults = new HashMap();
//        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, false);
//        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, "1.0.1");
//        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL,
//                "https://play.google.com/store/apps/details?id=com.mobile.android.qticketsapp&hl=en");
//
//        firebaseRemoteConfig.setDefaults(remoteConfigDefaults);
//        firebaseRemoteConfig.fetch(60) // fetch every minutes
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull NovoTicketPostBody<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "remote config is fetched.");
//                            firebaseRemoteConfig.activateFetched();
//                        }
//                    }
//                });
//


    }


    public class Config {
        public static final boolean DEVELOPER_MODE = false;
    }
}
*/
