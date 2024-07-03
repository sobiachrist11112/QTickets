package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OfferList implements Serializable {

    @SerializedName("events")
    @Expose
    public List<EventsFull> events = null;
    @SerializedName("tickets")
    @Expose
    public List<TicketFull> tickets = null;
    private final static long serialVersionUID = 1991034628254993813L;


}
