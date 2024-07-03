package com.production.qtickets.events;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateTime {

    public static String getFromMillies(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }

    public static String getFromMillies(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    public static String getFromMilliesInUTC(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(new Date(time));
    }

    public static String getFromMilliesInUTC(long time, String formate) {
        SimpleDateFormat format = new SimpleDateFormat(formate);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(new Date(time));
    }

    public static String getDateFromUTC(String datetime, String formatFrom,
                                        String formatTo) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat(formatFrom);
        SimpleDateFormat sdf2 = new SimpleDateFormat(formatTo);
        sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf2.format(sdf1.parse(datetime));
    }


    public static long getInMilliesFromUTC(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.parse(time).getTime();
    }

    public static long getInMillies(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.parse(time).getTime();
    }

    public static long getInMillies(String time, String formate)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formate);
        return format.parse(time).getTime();
    }

    public static String getNowInUTC() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(new Date(System.currentTimeMillis()));
    }

    public static String getNow() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(System.currentTimeMillis()));
    }

    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(System.currentTimeMillis()));
    }

    public static String getCurrentTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.getID();
    }

    public static String getDate(String datetime) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        return format2.format(format1.parse(datetime));
    }

    public static String getDate(Date datetime) throws ParseException {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        return format2.format(datetime);
    }

    public static String getTime(String datetime) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
        return format2.format(format1.parse(datetime));
    }

    public static String getDateTime(String datetime, String formatFrom,
                                     String formatTo) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat(formatFrom);
        SimpleDateFormat sdf2 = new SimpleDateFormat(formatTo);
        return sdf2.format(sdf1.parse(datetime));
    }

    public static String convertDateTimeBwTimeZone(String datetime,
                                                   String originalTimezone, String finalTimeZone)
            throws ParseException {
        String patternString = "yyyy-MM-dd HH:mm:ss";
        return convertDateTimeBwTimeZone(datetime, patternString,
                originalTimezone, finalTimeZone);
    }

    public static String convertDateTimeBwTimeZone(String datetime,
                                                   String patternString, String originalTimezone, String finalTimeZone)
            throws ParseException {
        try {
            DateFormat originalFormate = new SimpleDateFormat(patternString);
            originalFormate.setTimeZone(TimeZone.getTimeZone(originalTimezone));

            DateFormat finalFormat = new SimpleDateFormat(patternString);
            finalFormat.setTimeZone(TimeZone.getTimeZone(finalTimeZone));

            Date timestamp = originalFormate.parse(datetime);
            String output = finalFormat.format(timestamp);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datetime;
    }

    public static String getToday() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(calendar.getTime());
    }

    public static String getThisMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(calendar.getTime());
    }

    public static String getThisMonthLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(calendar.getTime());
    }

    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
    }

    /**
     * ISO 8601 strings
     * (in the following format: "2008-03-01T13:00:00+01:00"). It supports
     * parsing the "Z" timezone, but many other less-used features are
     * missing.
     */
    /**
     * Transform Calendar to ISO 8601 string.
     */
    private static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /**
     * Get current date and time formatted as ISO 8601 string.
     */
    public static String getNowInISO() {
        return fromCalendar(Calendar.getInstance());
    }

    /**
     * Get current date and time formatted as ISO 8601 string.
     */
    public static String getInISO(Calendar calendar) {
        return fromCalendar(calendar);
    }

    /**
     * Transform ISO 8601 string to Calendar.
     */
    public static Calendar toCalendarFromISO(final String iso8601string)
            throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "+00:00");
        try {
            s = s.substring(0, 22) + s.substring(23);  // to get rid of the ":"
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid length", 0);
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
        calendar.setTime(date);
        return calendar;
    }
}
