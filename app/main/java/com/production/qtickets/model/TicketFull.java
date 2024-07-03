package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TicketFull implements Serializable {

    @SerializedName("tktpriceid")
    @Expose
    public String tktpriceid;
    @SerializedName("isDelete")
    @Expose
    public String isDelete;
    @SerializedName("ticketname")
    @Expose
    public String ticketname;
    @SerializedName("tktmasterid")
    @Expose
    public String tktmasterid;
    @SerializedName("TotalTickets")
    @Expose
    public String totalTickets;
    @SerializedName("Availability")
    @Expose
    public String availability;
    @SerializedName("ServiceCharge")
    @Expose
    public String serviceCharge;
    @SerializedName("TicketPrice")
    @Expose
    public String ticketPrice;
    @SerializedName("EventCategoryId")
    @Expose
    public String eventCategoryId;
    @SerializedName("Admit")
    @Expose
    public String admit;
    @SerializedName("minAllowed")
    @Expose
    public String minAllowed;
    @SerializedName("maxAllowed")
    @Expose
    public String maxAllowed;
    @SerializedName("TicketLabel")
    @Expose
    public String ticketLabel;
    @SerializedName("Date")
    @Expose
    public String date;
    @SerializedName("Country")
    @Expose
    public String country;
    @SerializedName("Currency")
    @Expose
    public String currency;
    @SerializedName("NoOfTicketsPerTransaction")
    @Expose
    public String noOfTicketsPerTransaction;
    @SerializedName("dependent_id")
    @Expose
    public String dependentId;
    private final static long serialVersionUID = -6883676684149284827L;

}
