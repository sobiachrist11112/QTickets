package com.production.qtickets.utils;

/**
 * Created by hemanth on 4/10/2018.
 */

public class Config {

    // global topic to receive app wide push com.production.qtickets.notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "Q-Tickets";
//    public static final String YOUTUBE_API_KEY = "AIzaSyCXvEbvRsZuj40jTLegbvKJgnj26FlQflI";
}
