package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetHistoryData {
    @SerializedName("pkId")
    @Expose
    public Integer pkId;
    @SerializedName("eventId")
    @Expose
    public Integer eventId;
    @SerializedName("eventName")
    @Expose
    public String eventName;
    @SerializedName("eventDate")
    @Expose
    public String eventDate;
    @SerializedName("eventTime")
    @Expose
    public String eventTime;
    @SerializedName("venueName")
    @Expose
    public String venueName;
    @SerializedName("banner")
    @Expose
    public String banner;
    @SerializedName("orderId")
    @Expose
    public String orderId;
    @SerializedName("orderDate")
    @Expose
    public String orderDate;
    @SerializedName("orderTime")
    @Expose
    public String orderTime;
    @SerializedName("totalAmt")
    @Expose
    public String totalAmt;
    @SerializedName("seatDetails")
    @Expose
    public String seatDetails;
    @SerializedName("ticketDetails")
    @Expose
    public String ticketDetails;

}
