package com.production.qtickets.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

public class LocaleChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction (). compareTo (Intent.ACTION_LOCALE_CHANGED) == 0)
        {

            Log.v("LocaleChangedRecevier", "received ACTION_LOCALE_CHANGED");
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config,
                    context.getResources().getDisplayMetrics());
        }

    }
}