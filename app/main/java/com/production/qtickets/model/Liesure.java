package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Liesure {
    @SerializedName("TourDetails")
    @Expose
    public List<EventData> tourDetails = null;
    @SerializedName("pagingDetails")
    @Expose
    public EventData pagingDetails;
}
