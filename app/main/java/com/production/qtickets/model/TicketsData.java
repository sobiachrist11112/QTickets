package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketsData {

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
    public String TotalTickets;

    @SerializedName("Availability")
    @Expose
    public String Availability;

    @SerializedName("ServiceCharge")
    @Expose
    public String ServiceCharge;

    @SerializedName("TicketPrice")
    @Expose
    public String TicketPrice;

    @SerializedName("EventCategoryId")
    @Expose
    public String EventCategoryId;

    @SerializedName("Admit")
    @Expose
    public String Admit;

    @SerializedName("minAllowed")
    @Expose
    public String minAllowed;

    @SerializedName("maxAllowed")
    @Expose
    public String maxAllowed;

    @SerializedName("Date")
    @Expose
    public String Date;

    @SerializedName("NoOfTicketsPerTransaction")
    @Expose
    public String NoOfTicketsPerTransaction;

    public int getTicket_count() {
        return ticket_count;
    }

    public void setTicket_count(int ticket_count) {
        this.ticket_count = ticket_count;
    }

    public int ticket_count;
    public int totalTicketCost;
    public String ticketPriceID;
    public String tktPriceIDandCount;

    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


    public String getTktPriceIDandCount() {
        return tktPriceIDandCount;
    }

    public void setTktPriceIDandCount(String tktPriceIDandCount) {
        this.tktPriceIDandCount = tktPriceIDandCount;
    }

    public int getTotalTicketCost() {
        return totalTicketCost;
    }

    public void setTotalTicketCost(int totalTicketCost) {
        this.totalTicketCost = totalTicketCost;
    }

    public String getTicketPriceID() {
        return ticketPriceID;
    }

    public void setTicketPriceID(String ticketPriceID) {
        this.ticketPriceID = ticketPriceID;
    }
}
