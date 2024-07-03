package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventTicketQT5Type implements Serializable {
    @SerializedName("id")
    @Expose
    public int id = 0;

    @SerializedName("ticketName")
    @Expose
    public String ticketName = "";

    @SerializedName("categoryId")
    @Expose
    public int categoryId = 0;

    @SerializedName("category")
    @Expose
    public String category = "";

    @SerializedName("ticketDetails")
    @Expose
    public String ticketDetails = "";


    @SerializedName("quantity")
    @Expose
    public int quantity = 0;

    public int ticket_quantity;

    @SerializedName("price")
    @Expose
    public Double price = 0.0;

    @SerializedName("minAllowed")
    @Expose
    public int minAllowed = 0;

    @SerializedName("maxAllowed")
    @Expose
    public int maxAllowed = 0;

    @SerializedName("chargeType")
    @Expose
    public int chargeType = 0;

    @SerializedName("serviceCharge")
    @Expose
    public int serviceCharge = 0;

    @SerializedName("discountType")
    @Expose
    public int discountType = 0;

    @SerializedName("discount")
    @Expose
    public Double discount = 0.0;

    @SerializedName("admit")
    @Expose
    public int admit = 0;

    @SerializedName("ticketSalesEnd")
    @Expose
    public String ticketSalesEnd = "";

    @SerializedName("eventHourNday")
    @Expose
    public String eventHourNday = "";

    @SerializedName("beforeEvent")
    @Expose
    public String beforeEvent = "";

    @SerializedName("ticketcloseDate")
    @Expose
    public String ticketcloseDate = "";

    @SerializedName("daily")
    @Expose
    public String daily = "";

    @SerializedName("weekly")
    @Expose
    public String weekly = "";

    @SerializedName("monthly")
    @Expose
    public String monthly = "";

    @SerializedName("yearly")
    @Expose
    public String yearly = "";

    @SerializedName("dailyAvailableLimit")
    @Expose
    public String dailyAvailableLimit = "";

    @SerializedName("weeklyAvailableLimit")
    @Expose
    public String weeklyAvailableLimit = "";

    @SerializedName("monthlyAvailableLimit")
    @Expose
    public String monthlyAvailableLimit = "";

    @SerializedName("yearlyAvailableLimit")
    @Expose
    public String yearlyAvailableLimit = "";

    @SerializedName("bookingOpen")
    @Expose
    public String bookingOpen = "";

    @SerializedName("subChilds")
    @Expose
    public  String subChilds="";

    @SerializedName("chieldTicketXML")
    @Expose
    public String chieldTicketXML = "";

    @SerializedName("isIndividualTicket")
    @Expose
    public Boolean isIndividualTicket = false;


    @SerializedName("isFixPrice")
    @Expose
    public Boolean isFixPrice=false;

    public int getTicket_quantity() {
        return ticket_quantity;
    }

    public void setTicket_quantity(int ticket_quantity) {
        this.ticket_quantity = ticket_quantity;
    }


}
