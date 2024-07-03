package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harsh on 7/23/2018.
 */
public class NovoTicketDetailesModel {
    @SerializedName("ccode")
    @Expose
    public String ccode;
    @SerializedName("thumb")
    @Expose
    public String thumb;
    @SerializedName("qr_code_img")
    @Expose
    public String qrCodeImg;
    @SerializedName("movie_name")
    @Expose
    public String movieName;
    @SerializedName("cinema_name")
    @Expose
    public String cinemaName;
    @SerializedName("sdate")
    @Expose
    public String sdate;
    @SerializedName("show_time")
    @Expose
    public String showTime;
    @SerializedName("no_of_tickets")
    @Expose
    public String noOfTickets;
    @SerializedName("seats")
    @Expose
    public String seats;
    @SerializedName("screen_name")
    @Expose
    public String screenName;
    @SerializedName("amount")
    @Expose
    public String amount;
}
