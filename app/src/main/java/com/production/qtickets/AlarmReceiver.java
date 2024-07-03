package com.production.qtickets;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    NotificationManager nm;
    long pattern[] = {500, 500};
    private Uri notifsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    private NotificationCompat.BigTextStyle contentStyle;
    private List contentTexts;

    //the method will be fired when the alarm is triggerred
    @Override
    public void onReceive(Context context, Intent intent) {

        contentTexts = new ArrayList<String>();
        prepareContentTexts();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        Log.d("dayOfTheWeek", dayOfTheWeek);
        contentStyle = new androidx.core.app.NotificationCompat.BigTextStyle();
        if (dayOfTheWeek.equalsIgnoreCase("Monday")) {
            contentStyle.bigText((CharSequence) contentTexts.get(0));
        } else if (dayOfTheWeek.equalsIgnoreCase("Tuesday")) {
            contentStyle.bigText((CharSequence) contentTexts.get(1));
        } else if (dayOfTheWeek.equalsIgnoreCase("Wednesday")) {
            contentStyle.bigText((CharSequence) contentTexts.get(2));
        } else if (dayOfTheWeek.equalsIgnoreCase("Thursday")) {
            contentStyle.bigText((CharSequence) contentTexts.get(3));
        } else if (dayOfTheWeek.equalsIgnoreCase("Friday")) {
            contentStyle.bigText((CharSequence) contentTexts.get(4));
        } else if (dayOfTheWeek.equalsIgnoreCase("Saturday")) {
            contentStyle.bigText((CharSequence) contentTexts.get(5));
        } else if (dayOfTheWeek.equalsIgnoreCase("Sunday")) {
            contentStyle.bigText((CharSequence) contentTexts.get(6));
        }

        //  if(dayOfTheWeek.equalsIgnoreCase("Monday")||dayOfTheWeek.equalsIgnoreCase("Tuesday")
        //           ||dayOfTheWeek.equalsIgnoreCase("Wednesday")||dayOfTheWeek.equalsIgnoreCase("Thursday")) {
        NotificationCompat.Builder builder;
        builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.qticketsicon)
                .setContentTitle(context.getString(R.string.app_name))
                .setStyle(contentStyle)
                .setSound(notifsound)
                .setAutoCancel(true)
                .setVibrate(pattern);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, builder.build());
        //   }


    }

    public void prepareContentTexts() {
        contentTexts.add("Movie dates and Payday go hand in hand- Explore the latest movies near you.");
        contentTexts.add("Discover what's trending in entertainment world- Explore the most happening events near you");
        contentTexts.add("Still havent planned for weekend? Book your next live experience with ease- Explore Now");
        contentTexts.add("Go big this summer- Get stuck in & explore best family day-outs in your city");
        contentTexts.add("We've got tonnes of ideas to keep the kids entertained this weekend. Explore it here");
        contentTexts.add("No Time to be bored- Explore movies & events in your area.");
        contentTexts.add("Chartopping artists, showstoppers, comedy, sports & everything in between- Lets sort out your next live experience");
        contentTexts.add("Sport your team t-shirt & take a seat for the best sporting action in your city. Explore Now");
    }


}