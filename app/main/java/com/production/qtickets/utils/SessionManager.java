package com.production.qtickets.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Harsh on 7/31/2017.
 */

public class SessionManager {
    private final static int PRIVATE_MODE = 0;
    private final static String PREFERENCE_NAME = "Qtickets";
    private final static String COUNTRY_NAME = "country_name";
    private final static String COUNTRY_CODE = "country_code";
    private final static String COUNTRY_CURRENCY = "country_currency";
    private final static String COUNTRY_ID = "country_id";
    private final static String NAME = "name";
    private final static String EMAIL = "email";
    private final static String NUMBER = "number";
    private final static String NATINALITY = "nationality";
    private final static String PREFIX = "prefix";
    private final static String USER_ID = "user_id";
    private final static String MOVIE_ID = "movie_id";
    private final static String SHARE_URL = "shareurl";
    private final static String AGE_RISTRICT = "age_restrict";
    private final static String CENSOR = "censor";
    private final static String PASSWORD = "password";
    private final static String SELECTED_CINEMA = "selected_cinema";
    private final static String GENDER = "gender";
    private final static String SELECTED_COUNTRY_CODE = "selected_country_code";
    private final static String SELECTED_DOB = "selected_dob";
    private final static String DISCOUNT_AMOUNT = "discount_amount";
    private final static String DEVICE_TOKEN = "device_token";
    private final static String FAQ_LINK = "faq_link";
    private final static String ABOUT_LINK = "about_link";
    private final static String PRIVACY_LINK = "privacy_link";
    private final static String TERMS_LINK = "terms_link";
    private final static String CONTACTUSEMAIL = "contactemail";
    private final static String CONTACTSUSPHONEDUBAI = "contactphonedubai";
    private final static String CONTACTSUSPHONEQATAR = "contactphoneqatar";
    private final static String EVENT_ID = "event_id";
    private final static String fb_gplus_user = "fb_user";
    private final static String COVID_ALERT = "COVID_ALERT";
    private final static int BadgeCount = 0;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    @SuppressLint("WrongConstant")
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public String getAboutUsLink() {
        String device_token = pref.getString(ABOUT_LINK, "");
        return device_token;
    }

    public void setAboutUsLink(String faq) {
        editor.putString(ABOUT_LINK, faq);
        editor.commit();

    }

    public String getPrivacyLink() {
        String device_token = pref.getString(PRIVACY_LINK, "");
        return device_token;
    }

    public void setPrivacyLink(String faq) {
        editor.putString(PRIVACY_LINK, faq);
        editor.commit();

    }

    public String getTermsLink() {
        String device_token = pref.getString(TERMS_LINK, "");
        return device_token;
    }

    public void setTermsLink(String faq) {
        editor.putString(TERMS_LINK, faq);
        editor.commit();

    }

    public String getQatarContactPhone() {
        String device_token = pref.getString(CONTACTSUSPHONEQATAR, "");
        return device_token;
    }

    public void setQatarContactPhone(String faq) {
        editor.putString(CONTACTSUSPHONEQATAR, faq);
        editor.commit();

    }

    public String getDubaiContactPhone() {
        String device_token = pref.getString(CONTACTSUSPHONEDUBAI, "");
        return device_token;
    }

    public void setDubaiContactPhone(String faq) {
        editor.putString(CONTACTSUSPHONEDUBAI, faq);
        editor.commit();

    }

    public String getContactEmail() {
        String device_token = pref.getString(CONTACTUSEMAIL, "");
        return device_token;
    }

    public void setContactEmail(String faq) {
        editor.putString(CONTACTUSEMAIL, faq);
        editor.commit();

    }
    public String getFaqLink() {
        String device_token = pref.getString(FAQ_LINK, "");
        return device_token;
    }

    public void setfaqLink(String faq) {
        editor.putString(FAQ_LINK, faq);
        editor.commit();

    }

    public int getBadgeCount() {
        int Badge_Count = pref.getInt("BadgeCount",0);
        return Badge_Count;
    }

    public void setBadgeCount(int badgecount) {
        editor.putInt("BadgeCount",badgecount);
        editor.commit();
    }

    public Boolean isShowCovidAlert() {
        Boolean isShow = pref.getBoolean(COVID_ALERT,true);
        return isShow;
    }

    public void setShowCovidAlert(Boolean isShow) {
        editor.putBoolean(COVID_ALERT,isShow);
        editor.commit();
    }

    public String getCountryName() {
        String country_name = pref.getString(COUNTRY_NAME, "");
        return country_name;
    }

    public void setCountryName(String countryName) {
        editor.putString(COUNTRY_NAME, countryName);
        editor.commit();

    }

    public  String getDiscountAmount() {
        String discount_amount = pref.getString(DISCOUNT_AMOUNT,"");
        return discount_amount;
    }

    public void setDiscountAmount(String discountAmount ){
        editor.putString(DISCOUNT_AMOUNT,discountAmount);
        editor.commit();
    }

    public String getCountryCode() {
        String country_code = pref.getString(COUNTRY_CODE, "");
        return country_code;
    }

    public void setCountryCode(String countryCode) {
        editor.putString(COUNTRY_CODE, countryCode);
        editor.commit();

    }

    public String getCountryCurrency() {
        String country_currency = pref.getString(COUNTRY_CURRENCY, "");
        return country_currency;
    }

    public void setCountryCurrency(String countryCurrency) {
        editor.putString(COUNTRY_CURRENCY, countryCurrency);
        editor.commit();
    }

    public int getCountryID() {
        int country_ID = pref.getInt(COUNTRY_ID, -1);
        return country_ID;
    }

    public void setCountryID(int countryID) {
        editor.putInt(COUNTRY_ID, countryID);
        editor.commit();
    }

    public String getName() {
        String name = pref.getString(NAME, "");
        return name;
    }

    public void setName(String name) {
        editor.putString(NAME, name);
        editor.commit();
    }

    public String getEmail() {
        String email = pref.getString(EMAIL, "");
        return email;
    }

    public void setEmail(String email) {
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public String getNumber() {
        String number = pref.getString(NUMBER, "");
        return number;
    }

    public void setNumber(String number) {
        editor.putString(NUMBER, number);
        editor.commit();
    }

    public String getNatinality() {
        String nationality = pref.getString(NATINALITY, "");
        return nationality;
    }

    public void setNatinality(String natinality) {
        editor.putString(NATINALITY, natinality);
        editor.commit();
    }

    public String getPrefix() {
        String prefix = pref.getString(PREFIX, "");
        return prefix;
    }

    public void setPrefix(String prefix) {
        editor.putString(PREFIX, prefix);
        editor.commit();
    }

    public String getUserId() {
        String prefix = pref.getString(USER_ID, "");
        return prefix;
    }

    public void setUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public String getMovieId() {
        String movie_id = pref.getString(MOVIE_ID, "");
        return movie_id;
    }

    public void setMovieId(String movie_id) {
        editor.putString(MOVIE_ID, movie_id);
        editor.commit();
    }

    public String getShareUrl() {
        String share_url = pref.getString(SHARE_URL, "");
        return share_url;
    }

    public void setShareUrl(String share_url) {
        editor.putString(SHARE_URL, share_url);
        editor.commit();
    }

    public void setAgeRestrict_Censor(String AgeRestrictRating, String censor) {
        editor.putString(AGE_RISTRICT, AgeRestrictRating);
        editor.putString(CENSOR, censor);
        editor.commit();
    }

    public String getAgeRistrict() {
        String age_restrict = pref.getString(AGE_RISTRICT, "");
        return age_restrict;
    }

    public String getCensor() {
        String censor = pref.getString(CENSOR, "");
        return censor;
    }

    public String getPassword() {
        String password = pref.getString(PASSWORD, "");
        return password;
    }

    public void setPassword(String password) {
        editor.putString(PASSWORD, password);
        editor.commit();
    }
    public String getSelectedCinema() {
        String selected_cinema = pref.getString(SELECTED_CINEMA, "");
        return selected_cinema;
    }

    public void setSelectedCinema(String selectedCinema) {
        editor.putString(SELECTED_CINEMA, selectedCinema);
        editor.commit();
    }
    public String getGender() {
        String gender = pref.getString(GENDER, "");
        return gender;
    }

    public void setGender(String gender) {
        editor.putString(GENDER, gender);
        editor.commit();
    }
    public String getSelectedCountryCode() {
        String selected_country_code = pref.getString(SELECTED_COUNTRY_CODE, "");
        return selected_country_code;
    }

    public void setSelectedCountryCode(String selected_country_code) {
        editor.putString(SELECTED_COUNTRY_CODE, selected_country_code);
        editor.commit();
    }

    public void setDob(String dob) {
        editor.putString(SELECTED_DOB, dob);
        editor.commit();
    }

    public String getdob() {
        String dob = pref.getString(SELECTED_DOB, "");
        return dob;
    }

    public String getDeviceToken() {
        String device_token = pref.getString(DEVICE_TOKEN, "");
        return device_token;
    }

    public void setDeviceToken(String device_token) {
        editor.putString(DEVICE_TOKEN, device_token);
        editor.commit();

    }
    public String getEventId() {
        String event_id = pref.getString(EVENT_ID, "");
        return event_id;
    }

    public void setEventId(String event_id) {
        editor.putString(EVENT_ID, event_id);
        editor.commit();

    }
    public String getFb_gplus_user() {
        String fb_user = pref.getString(fb_gplus_user, "");
        return fb_user;
    }

    public void setFb_gplus_user(String fb_user) {
        editor.putString(fb_gplus_user, fb_user);
        editor.commit();

    }

    public void clearAllSession(){

        setBadgeCount(0);
        setCountryName("");
        setDiscountAmount("");
        setCountryCode("");
        setCountryCurrency("");
        setCountryID(0);
        setName("");
        setEmail("");
        setNumber("");
        setNatinality("");
        setPrefix("");
        setUserId("");
        setFb_gplus_user("");
        setEventId("");
        setDeviceToken("");
        setDob("");
        setSelectedCinema("");
        setSelectedCountryCode("");
        setGender("");
        setMovieId("");
        setPassword("");
        setShareUrl("");
        setAgeRestrict_Censor("","");

       /* editor.clear();
        editor.apply();*/
    }

}
