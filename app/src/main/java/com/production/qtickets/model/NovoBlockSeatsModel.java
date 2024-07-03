package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Harsh on 7/17/2018.
 */
public class NovoBlockSeatsModel {
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("movieTitle")
    @Expose
    public String movieTitle;
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("cinemaName")
    @Expose
    public String cinemaName;
    @SerializedName("cinemaClass")
    @Expose
    public List<String> cinemaClass = null;
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
    @SerializedName("totalPrice")
    @Expose
    public String totalPrice;
    @SerializedName("bookingInfoId")
    @Expose
    public String bookingInfoId;
    @SerializedName("userSessionID")
    @Expose
    public String userSessionID;
    @SerializedName("bookingFee")
    @Expose
    public String bookingFee;
    @SerializedName("extraDescription")
    @Expose
    public String extraDescription;
    @SerializedName("vatText")
    @Expose
    public String vatText;
    @SerializedName("fnbCredit")
    @Expose
    public Integer fnbCredit;
    @SerializedName("totalPriceValue")
    @Expose
    public Integer totalPriceValue;
    @SerializedName("fnbVoucher")
    @Expose
    public Object fnbVoucher;
    @SerializedName("foodDescp")
    @Expose
    public Object foodDescp;
}
