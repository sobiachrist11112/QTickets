package com.production.qtickets.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.legacy.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Set;

import me.leolin.shortcutbadger.ShortcutBadger;

public class FirebaseDataReceiver extends WakefulBroadcastReceiver {

    private final String TAG = "FirebaseDataReceiver";

    private int notificationId = -1;
    private NotificationManager mNotificationManager;

    int badgeCount = 0;

    Context context;

    SharedPreferences prefmPrefs;
    SharedPreferences.Editor editor;


    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "I'm in!!!");

        try {
            if (intent != null) {
                Log.d("message payload","Firebase is"+intent.getDataString() +","+intent.getExtras());
              //  Bundle b = intent.getExtras();
               /* Set s = b.keySet();
                for(String key: b.keySet()){
                    Log.d("bundle","key is"+key+"  "+b.get(key));
                }*/
                mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Log.v("notificationIdb", "== " + notificationId);
                mNotificationManager.cancel(notificationId);
                notificationId++;

                prefmPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                editor = prefmPrefs.edit();


                // getting the count of notifications in the notification tray, which are diplaying in the badge count
                int notificationIDCount = prefmPrefs.getInt("notificationId", 0);

                if (notificationIDCount >= 0) {
                    ++notificationIDCount;
                    ShortcutBadger.applyCount(context, notificationIDCount);
                    Log.v("notificationIda", "== " + notificationIDCount);
                    //storing the notification count in shared preferences
                    editor.putInt("notificationId", notificationIDCount);
                    editor.commit();
                } else {
                    editor.putInt("notificationId", notificationId);
                    editor.commit();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
