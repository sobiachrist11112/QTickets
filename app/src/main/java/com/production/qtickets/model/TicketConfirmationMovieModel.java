package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harsh on 6/27/2018.
 */
public class TicketConfirmationMovieModel {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("movie")
    @Expose
    public String movie;
    @SerializedName("theater")
    @Expose
    public String theater;
    @SerializedName("area")
    @Expose
    public String area;
    @SerializedName("bookedtime")
    @Expose
    public String bookedtime;
    @SerializedName("showdate")
    @Expose
    public String showdate;
    @SerializedName("seats")
    @Expose
    public String seats;
    @SerializedName("seatsCount")
    @Expose
    public String seatsCount;
    @SerializedName("confirmationCode")
    @Expose
    public String confirmationCode;
    @SerializedName("barcodeURL")
    @Expose
    public String barcodeURL;
    @SerializedName("ticket_Cost")
    @Expose
    public String ticketCost;
    @SerializedName("total_Cost")
    @Expose
    public String totalCost;
    @SerializedName("bookingfees")
    @Expose
    public String bookingfees;
    @SerializedName("movieImageURL")
    @Expose
    public String movieImageURL;
    @SerializedName("moviebannerURL")
    @Expose
    public String moviebannerURL;
    @SerializedName("movieServerID")
    @Expose
    public String movieServerID;
    @SerializedName("Genre")
    @Expose
    public String genre;
    @SerializedName("ScreenName")
    @Expose
    public String screenName;
    @SerializedName("checkcancelstatus")
    @Expose
    public String checkcancelstatus;
}
