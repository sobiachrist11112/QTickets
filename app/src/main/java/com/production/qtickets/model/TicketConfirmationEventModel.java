package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harsh on 6/27/2018.
 */
public class TicketConfirmationEventModel {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("Eventid")
    @Expose
    public String eventid;
    @SerializedName("total_Cost")
    @Expose
    public String totalCost;
    @SerializedName("BookedOn")
    @Expose
    public String bookedOn;
    @SerializedName("seatsCount")
    @Expose
    public String seatsCount;
    @SerializedName("seats")
    @Expose
    public String seats;
    @SerializedName("Event")
    @Expose
    public String event;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("TicketName")
    @Expose
    public String ticketName;
    @SerializedName("eventDate")
    @Expose
    public String eventDate;
    @SerializedName("eventTime")
    @Expose
    public String eventTime;
    @SerializedName("confirmationCode")
    @Expose
    public String confirmationCode;
    @SerializedName("eventImageURL")
    @Expose
    public String eventImageURL;
}
