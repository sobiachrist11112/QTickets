package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel {
    @SerializedName("PushNotificationId")
    @Expose
    public String pushNotificationId;
    @SerializedName("PushNotificationType")
    @Expose
    public String pushNotificationType;
    @SerializedName("PushNotificationCat")
    @Expose
    public String pushNotificationCat;
    @SerializedName("PushNotificationShortMsg")
    @Expose
    public String pushNotificationShortMsg;
    @SerializedName("PushNotificationMsg")
    @Expose
    public String pushNotificationMsg;
    @SerializedName("PushNotificationInsertTime")
    @Expose
    public String pushNotificationInsertTime;
    @SerializedName("imgpath")
    @Expose
    public String imgpath;
    @SerializedName("SourceType")
    @Expose
    public String SourceType;
    @SerializedName("SoruceID")
    @Expose
    public String SoruceID;
    @SerializedName("QTurl")
    @Expose
    public String QTurl;
    @SerializedName("show_browser")
    @Expose
    public String show_browser;
    @SerializedName("event_name")
    @Expose
    public String event_name;
    @SerializedName("event_status")
    @Expose
    public String event_status;
    @SerializedName("SeatLayout")
    @Expose
    public String SeatLayout;
    @SerializedName("single_ticket_selection")
    @Expose
    public String single_ticket_selection;
    @SerializedName("event_startdate")
    @Expose
    public String event_startdate;
    @SerializedName("event_enddate")
    @Expose
    public String event_enddate;
    @SerializedName("event_country")
    @Expose
    public String event_country;
    @SerializedName("event_location")
    @Expose
    public String event_location;
    @SerializedName("event_id")
    @Expose
    public String event_id;
    @SerializedName("event_category")
    @Expose
    public String event_category;
}
