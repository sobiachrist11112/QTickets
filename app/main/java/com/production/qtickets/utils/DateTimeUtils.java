package com.production.qtickets.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static String getTodayDate() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(today);
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    public static String getMonthLastDate() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(today);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();

        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        return sdf.format(lastDayOfMonth);
    }

    public static String getWeekLastDate() {
        Date today = new Date();

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(today);
        calendar.add(Calendar.DATE, +7);

        Date lastDayOfMonth = calendar.getTime();

        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        return sdf.format(lastDayOfMonth);
    }

    public static String getDayName(String strDate) {
        String dayName = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date d1 = dateFormat.parse(strDate);
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            Date d2 = dateFormat.parse(dateFormat.format(calendar.getTime()));
            if(d1.compareTo(d2) == 0) {
                dayName =  "Today";
            }else {
                SimpleDateFormat outFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
                dayName = outFormat.format(d1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayName;
    }

    public static String getEventDate(String strDate) {
        String eventDate = "";
        String[] suffixes =
                //    0     1     2     3     4     5     6     7     8     9
                { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    10    11    12    13    14    15    16    17    18    19
                        "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                        //    20    21    22    23    24    25    26    27    28    29
                        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    30    31
                        "th", "st" };
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date d1 = dateFormat.parse(strDate);
            SimpleDateFormat outFormat = new SimpleDateFormat("MMM d", Locale.ENGLISH);
            SimpleDateFormat outDate = new SimpleDateFormat("d", Locale.ENGLISH);
            eventDate = outFormat.format(d1)+suffixes[Integer.parseInt(outDate.format(d1))];

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return eventDate;
    }


    public static String getEventFullDate(String strDate) {
        String eventDate = "";
        String[] suffixes =
                //    0     1     2     3     4     5     6     7     8     9
                { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    10    11    12    13    14    15    16    17    18    19
                        "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                        //    20    21    22    23    24    25    26    27    28    29
                        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    30    31
                        "th", "st" };
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date d1 = dateFormat.parse(strDate);
            SimpleDateFormat outFormatDate = new SimpleDateFormat("MMMM d", Locale.ENGLISH);
            SimpleDateFormat outFormatDay = new SimpleDateFormat("EEEE", Locale.ENGLISH);
            SimpleDateFormat outDate = new SimpleDateFormat("d", Locale.ENGLISH);
            eventDate = outFormatDate.format(d1)+suffixes[Integer.parseInt(outDate.format(d1))]+", "+outFormatDay.format(d1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return eventDate;
    }


    public static String getEventFullDateSecond(String strDate) {
        String eventDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date d1 = dateFormat.parse(strDate);
            SimpleDateFormat outFormatDate = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);
            eventDate = outFormatDate.format(d1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return eventDate;
    }


    public static String getEventTime(String strDate) {
        String eventDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
            Date d1 = dateFormat.parse(strDate);
            SimpleDateFormat outFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            eventDate = outFormat.format(d1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return eventDate;
    }
}
