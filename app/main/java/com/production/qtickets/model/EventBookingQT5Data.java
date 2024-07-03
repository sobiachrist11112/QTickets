package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventBookingQT5Data {

    @SerializedName("id")
    @Expose
    public int id = 0;

    @SerializedName("eventTitle")
    @Expose
    public String eventTitle = "";

    @SerializedName("eventDate")
    @Expose
    public String eventDate = "";

    @SerializedName("eventTime")
    @Expose
    public String eventTime = "";

    @SerializedName("venue")
    @Expose
    public String venue = "";

    @SerializedName("countryId")
    @Expose
    public int countryId = 0;

    @SerializedName("currencyCode")
    @Expose
    public String currencyCode = "";

    @SerializedName("isSingleSelection")
    @Expose
    public Boolean isSingleSelection = false;

    @SerializedName("eventBookingDates")
    @Expose
    public List<EventBookingQT5Date> eventBookingDates = null;

    @SerializedName("eventTickets")
    @Expose
    public List<EventTicketQT5Type> eventTickets = null;




}
