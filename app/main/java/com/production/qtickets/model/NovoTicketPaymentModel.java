package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Harsh on 7/20/2018.
 */
public class NovoTicketPaymentModel {
    @SerializedName("status")
    @Expose
    public NovoTicketPaymentStausModel status;
    @SerializedName("bookingID")
    @Expose
    public String bookingID;
    @SerializedName("movieTitle")
    @Expose
    public String movieTitle;
    @SerializedName("movieRating")
    @Expose
    public String movieRating;
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("cinemaName")
    @Expose
    public String cinemaName;
    @SerializedName("cinemaClass")
    @Expose
    public String cinemaClass;
    @SerializedName("screenName")
    @Expose
    public String screenName;
    @SerializedName("showDate")
    @Expose
    public String showDate;
    @SerializedName("showTime")
    @Expose
    public String showTime;
    @SerializedName("noofSeats")
    @Expose
    public String noofSeats;
    @SerializedName("seatList")
    @Expose
    public String seatList;
    @SerializedName("totalTicketPrice")
    @Expose
    public String totalTicketPrice;
    @SerializedName("bookingInfoId")
    @Expose
    public String bookingInfoId;
    @SerializedName("foodAmount")
    @Expose
    public String foodAmount;
    @SerializedName("foodDesscp")
    @Expose
    public String foodDesscp;
    @SerializedName("ticketDescp")
    @Expose
    public List<String> ticketDescp = null;
    @SerializedName("totalPrice")
    @Expose
    public String totalPrice;
    @SerializedName("barcodeImgUrl")
    @Expose
    public String barcodeImgUrl;
    @SerializedName("bookingFee")
    @Expose
    public String bookingFee;
    @SerializedName("extraDescription")
    @Expose
    public String extraDescription;
    @SerializedName("foodDescp")
    @Expose
    public List<String> foodDescp = null;
    @SerializedName("vatText")
    @Expose
    public String vatText;
}
