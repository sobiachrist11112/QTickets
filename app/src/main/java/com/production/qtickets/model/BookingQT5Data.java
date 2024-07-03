package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BookingQT5Data implements Serializable {

    @SerializedName("pkId")
    @Expose
    public long pkId = 0;

    @SerializedName("ticks")
    @Expose
    public long ticks = 0;

    @SerializedName("orderId")
    @Expose
    public String orderID = "";

    @SerializedName("eventName")
    @Expose
    public String eventName = "";

    @SerializedName("orderDate")
    @Expose
    public String orderDate = "";

    @SerializedName("venueName")
    @Expose
    public String venueName = "";

    @SerializedName("totalAmt")
    @Expose
    public Double totalAmt = 0.0;

    @SerializedName("currencyCode")
    @Expose
    public String currencyCode = "";

    @SerializedName("barcodeFileName")
    @Expose
    public String barcodeFileName = "";

    @SerializedName("aepgFormDetails")
    @Expose
    public String aepgFormDetails = "";

    @SerializedName("orderTime")
    @Expose
    public String orderTime = "";

    @SerializedName("orderIds")
    @Expose
    public List<String> orderIds = null;

    @SerializedName("barcodes")
    @Expose
    public List<String> barcodes = null;

}
