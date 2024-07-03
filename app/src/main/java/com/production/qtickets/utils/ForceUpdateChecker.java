package com.production.qtickets.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;
import com.production.qtickets.model.DialogData;

public class ForceUpdateChecker {
    private static final String TAG = ForceUpdateChecker.class.getSimpleName();

    public static final String KEY_UPDATE_REQUIRED = "force_update_required";
    public static final String KEY_CURRENT_VERSION = "force_update_current_version";
    public static final String KEY_UPDATE_URL = "force_update_store_url";
    public static final String KEY_SOFT_UPDATE_URL = "soft_update_required";
    public static final String KEY_UPDATE_REQUIRED_HUAWEI = "huawei_force_update_required";
    public static final String KEY_CURRENT_VERSION_HUAWEI = "huawei_force_update_current_version";
    public static final String KEY_UPDATE_URL_HUAWEI = "huawei_force_update_store_url";
    public static final String KEY_SOFT_UPDATE_URL_HUAWEI = "huawei_soft_update_required";

    public static final String FLEXI_POPUP_DETAILS = "flexi_popup_android";
    public static final String SHOW_FLEXI_ANDROID = "showflexiandroid";
    private OnUpdateNeededListener onUpdateNeededListener;
    private Context context;
    private boolean isHuawei = false;//change before publishing app
    private boolean isPlaystore = true; //change before publishing app

    public interface OnUpdateNeededListener {
        void onUpdateNeeded(String updateUrl, boolean isSoft, boolean isForce);
    }

    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    public ForceUpdateChecker(@NonNull Context context,
                              OnUpdateNeededListener onUpdateNeededListener) {
        this.context = context;
        this.onUpdateNeededListener = onUpdateNeededListener;
    }

    public void check() {

        if (isHuawei) {
            final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
            if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED_HUAWEI)) {
                String currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION_HUAWEI);
                String appVersion = getAppVersion(context);
                String updateUrl = remoteConfig.getString(KEY_UPDATE_URL_HUAWEI);
                if (!TextUtils.equals(currentVersion, appVersion)
                        && onUpdateNeededListener != null) {
                    onUpdateNeededListener.onUpdateNeeded(updateUrl, remoteConfig.getBoolean(KEY_SOFT_UPDATE_URL_HUAWEI), remoteConfig.getBoolean(
                            KEY_UPDATE_REQUIRED_HUAWEI));
                }

            }
            if (remoteConfig.getBoolean(KEY_SOFT_UPDATE_URL_HUAWEI)) {
                String currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION_HUAWEI);
                String appVersion = getAppVersion(context);
                String updateUrl = remoteConfig.getString(KEY_UPDATE_URL_HUAWEI);
                if (!TextUtils.equals(currentVersion, appVersion)
                        && onUpdateNeededListener != null) {
                    onUpdateNeededListener.onUpdateNeeded(updateUrl, remoteConfig.getBoolean(KEY_SOFT_UPDATE_URL_HUAWEI), remoteConfig.getBoolean(KEY_UPDATE_REQUIRED_HUAWEI));
                }

            }
        }

        if (isPlaystore) {

            final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

            String currentVersion1 = remoteConfig.getString(KEY_CURRENT_VERSION);
            String value = remoteConfig.getString(FLEXI_POPUP_DETAILS);
            boolean showFlexiPopup = remoteConfig.getBoolean(SHOW_FLEXI_ANDROID);

            Log.d("22dec", "value: " + showFlexiPopup);
            Log.d("22dec", "currentVersion: " + currentVersion1 + "App version :" + getAppVersion(context));
            Log.d("22dec", "Force update val : " + remoteConfig.getBoolean(KEY_UPDATE_REQUIRED));

//          Toast.makeText(context, " " + remoteConfig.getBoolean(KEY_UPDATE_REQUIRED), Toast.LENGTH_SHORT).show();

            if (showFlexiPopup) {

                // Parse the JSON response using Gson
                Gson gson = new Gson();
                DialogData dialogData = gson.fromJson(value, DialogData.class);

                // Build and show the AlertDialog
                showCustomDialog(dialogData);

            }

            if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)) {
                String currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION);

                String appVersion = getAppVersion(context);

                String updateUrl = remoteConfig.getString(KEY_UPDATE_URL);
                if (!TextUtils.equals(currentVersion, appVersion)
                        && onUpdateNeededListener != null) {

//                  Toast.makeText(context,"onUpdateNeeded ",Toast.LENGTH_SHORT).show();

                    onUpdateNeededListener.onUpdateNeeded(updateUrl, remoteConfig.getBoolean(KEY_SOFT_UPDATE_URL), remoteConfig.getBoolean(KEY_UPDATE_REQUIRED));
                }

            }
            if (remoteConfig.getBoolean(KEY_SOFT_UPDATE_URL)) {
                String currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION);
                String appVersion = getAppVersion(context);
                String updateUrl = remoteConfig.getString(KEY_UPDATE_URL);
                if (!TextUtils.equals(currentVersion, appVersion)
                        && onUpdateNeededListener != null) {
                    onUpdateNeededListener.onUpdateNeeded(updateUrl, remoteConfig.getBoolean(KEY_SOFT_UPDATE_URL), remoteConfig.getBoolean(KEY_UPDATE_REQUIRED));
                }

            }
        }

    }


    private void showCustomDialog(DialogData dialogData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the dialog title and message dynamically
        builder.setTitle(dialogData.getHeading())
                .setMessage(dialogData.getDescription())
                .setCancelable(false) // Set to true if you want the dialog to be dismissible by tapping outside

                // Set positive button and its listener
                .setPositiveButton(dialogData.getPosstive_btn(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle positive button click
                        // You can perform actions here or leave it empty
                    }
                });

        // Set negative button and its listener
               /* .setNegativeButton(dialogData.getNegative_btn(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle negative button click
                        // You can perform actions here or leave it empty
                    }
                });*/

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private String getAppVersion(Context context) {
        String result = "";

        try {
            result = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public static class Builder {

        private Context context;
        private OnUpdateNeededListener onUpdateNeededListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder onUpdateNeeded(OnUpdateNeededListener onUpdateNeededListener) {
            this.onUpdateNeededListener = onUpdateNeededListener;
            return this;
        }

        public ForceUpdateChecker build() {
            return new ForceUpdateChecker(context, onUpdateNeededListener);
        }

        public ForceUpdateChecker check() {
            ForceUpdateChecker forceUpdateChecker = build();
            forceUpdateChecker.check();

            return forceUpdateChecker;
        }
    }
}