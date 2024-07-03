package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class SeatClassModel {

    @SerializedName("maxBooking")
    @Expose
    public String maxBooking;
    @SerializedName("bookingFees")
    @Expose
    public String bookingFees;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("Class")
    @Expose
    public List<ClassModel> classModel=null;
}
