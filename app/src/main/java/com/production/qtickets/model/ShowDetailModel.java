package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Harsh on 5/29/2018.
 */
public class ShowDetailModel implements Serializable {
    @SerializedName("show_id")
    @Expose
    public String showId;
    @SerializedName("cid")
    @Expose
    public String cid;
    @SerializedName("screen_name")
    @Expose
    public String screenName;
    @SerializedName("screeentype")
    @Expose
    public String screeentype;
    @SerializedName("show_time")
    @Expose
    public String showTime;
    @SerializedName("Show_Date")
    @Expose
    public String showDate;
    @SerializedName("seats_available")
    @Expose
    public String seatsAvailable;
    @SerializedName("totalGoing")
    @Expose
    public String totalGoing;
    @SerializedName("show_status")
    @Expose
    public String showStatus;
    @SerializedName("total_seats")
    @Expose
    public String totalSeats;
    @SerializedName("Screen_Id")
    @Expose
    public String screenId;
    @SerializedName("variant_id")
    @Expose
    public String variantId;
    @SerializedName("flik_movie_id")
    @Expose
    public String flik_movie_id;
    @SerializedName("sessionId")
    @Expose
    public String sessionId;
    @SerializedName("Novo_movie_id")
    @Expose
    public String Novo_movie_id;


}
