package com.production.qtickets.services;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.production.qtickets.R;
import com.production.qtickets.activity.SplashscreenActivity;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.events.EventDetails.EventDetailsActivity;
import com.production.qtickets.events.EventDetails.EventWebviewActivity;
import com.production.qtickets.moviedetailes.MovieDetailsActivity;
import com.production.qtickets.utils.MySingleton;
import com.production.qtickets.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;

import com.production.qtickets.notifications.MyNotificationActivity;
import com.production.qtickets.utils.SessionManager;

import java.util.Calendar;

import me.leolin.shortcutbadger.ShortcutBadger;

import static android.app.PendingIntent.getActivity;

/**
 * Created by hemanth on 4/10/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;

    Context context;

    int badgeCount = 0;
    SessionManager sessionManager;
    String CHANNEL_ID = "channel_id";
    String showBrowser,notify_Category,movieEventId;
    public static final int NOTIFICATION_ID = 1;
    NotificationCompat.Builder builder;



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sessionManager = new SessionManager(getApplicationContext());
        Log.d("InMessage receive","payload id"+remoteMessage);
        if (remoteMessage == null)
            return;
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
        }
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                final String title, message, img_url,event_redirect,url;
                title = remoteMessage.getData().get("title");
                message = remoteMessage.getData().get("body");
                img_url = remoteMessage.getData().get("image");
                url = remoteMessage.getData().get("url");
                event_redirect = remoteMessage.getData().get("eventRedirect");
                showBrowser = remoteMessage.getData().get("showBrowser");
                notify_Category = remoteMessage.getData().get("notifyCategory");
                movieEventId = remoteMessage.getData().get("movieEventID");
                Intent intent = null;
                if(notify_Category.equals("0")){
                    if (url != null && !url.equals("")){
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                    }else {
                        intent = new Intent(this, DashBoardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                } else if(notify_Category.equals("1")){
                    if(movieEventId != null){
                        intent = new Intent(this, MovieDetailsActivity.class);
                        intent.putExtra("movie_id",movieEventId);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                } else {
                    if(showBrowser.equals("1")){
                        intent = new Intent(this, EventWebviewActivity.class);
                        intent.putExtra("redirect_url",event_redirect);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else {
                        intent = new Intent(this, EventDetailsActivity.class);
                        intent.putExtra("event_id",movieEventId);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                }


               /* Intent intent = new Intent(this, MyNotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
                PendingIntent pendingIntent;
                pendingIntent = (getActivity(MyFirebaseMessagingService.this, 0,
                        intent, PendingIntent.FLAG_ONE_SHOT));
               /* pendingIntent = (getActivity(MyFirebaseMessagingService.this, Calendar.getInstance().get(Calendar.MILLISECOND),
                        intent,android.content.Intent.FLAG_ACTIVITY_NEW_TASK));*/
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder = new NotificationCompat.Builder(MyFirebaseMessagingService.this,"null");
                builder.setContentTitle(title);
                builder.setContentText(message);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                //builder.setStyle(inboxStyle);
                builder.setSound(uri);
                // builder.setChannelId(CHANNEL_ID);
                builder.setSmallIcon(R.mipmap.qticketsicon);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                // builder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
                // RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                ImageRequest imageRequest = new ImageRequest(img_url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        if (message.length() > 40) {
                            builder.setCustomBigContentView(remoteView(title, message, response));
                        }else {
                            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response).setSummaryText(message));
                        }
                        notificationBuild();
                        //notificationManager.cancel(NOTIFICATION_ID);
                    }
                }, 200, 200, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,"Error message" + error.getMessage());
                        notificationBuild();
                    }
                });

              /*  NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel_id = new NotificationChannel(CHANNEL_ID,"Notification",NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel_id);
                    builder.setChannelId(CHANNEL_ID);
                    builder.setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE);
                } else {
                    badgeCount = sessionManager.getBadgeCount() + 1;
                    sessionManager.setBadgeCount(badgeCount);
                    ShortcutBadger.applyCount(MyFirebaseMessagingService.this, badgeCount);
                }
                notificationManager.notify(NOTIFICATION_ID, builder.build());*/
                //  requestQueue.add(imageRequest);
                MySingleton.getmInstance(this).addToRequestQue(imageRequest);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        try {
            //find the home launcher Package
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            String currentHomePackage = resolveInfo.activityInfo.packageName;
            // SplashscreenActivity.textViewHomePackage.setText(getResources().getString(R.string.luncher) + currentHomePackage);

        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }

    private void notificationBuild(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel_id = new NotificationChannel(CHANNEL_ID,"Notification",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel_id);
            builder.setChannelId(CHANNEL_ID);
            builder.setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE);
        } else {
            badgeCount = sessionManager.getBadgeCount() + 1;
            sessionManager.setBadgeCount(badgeCount);
            ShortcutBadger.applyCount(MyFirebaseMessagingService.this, badgeCount);
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String refreshedToken = s;
        // Saving reg id to shared preferences
        storeRegIdInPref(s);

        // sending reg id to your server
        sendRegistrationToServer(s);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", s);
        Log.d("FirebaseToken :",s.toString());
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.setDeviceToken(token);
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MyNotificationActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

    @SuppressLint("RemoteViewLayout")
    public RemoteViews remoteView(String title, String message, Bitmap bitmap)
    {
        RemoteViews views;
        views = new RemoteViews(getPackageName(), R.layout.custom_notification);
        views.setImageViewBitmap(R.id.img, bitmap);
        views.setTextViewText(R.id.txt_title, title);
//        views.setImageViewBitmap(R.id.YOUR_APP_ID_FROM_LAYOUT, BitmapFactory.decodeResource(getResources(), R.drawable.APP_ICON_OF_YOUR_APP));
        views.setTextViewText(R.id.txt_message, message);
        return views;
    }
}
