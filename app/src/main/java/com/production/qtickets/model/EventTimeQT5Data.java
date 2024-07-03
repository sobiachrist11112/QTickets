package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventTimeQT5Data {

    @SerializedName("eventDate")
    @Expose
    public String eventDate = "";

    @SerializedName("startTime")
    @Expose
    public String startTime = "";

    @SerializedName("endTime")
    @Expose
    public String endTime = "";


    @SerializedName("venue")
    @Expose
    public String venue="";


    @Override
    public String toString() {
//        if()
//
//
//        if (startTime.equals(""))return "" else ;
//        if (endTime == null || endTime.equals("") || endTime.equals("null"))return "";
        return startTime + " - " + endTime;
    }
}
