package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Harsh on 5/29/2018.
 */
public class ItemModel implements Serializable{
    @SerializedName("cinema_id")
    @Expose
    public String cinemaId;
    @SerializedName("cinema_name")
    @Expose
    public String cinemaName;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("cinema_logo")
    @Expose
    public String cinemaLogo;
    @SerializedName("longitude")
    @Expose
    public String longitude;
    @SerializedName("lattitude")
    @Expose
    public String lattitude;
    @SerializedName("ArabicName")
    @Expose
    public String arabicName;
    @SerializedName("show_details")
    @Expose
    public List<ShowDetailModel> showDetails = null;

}
