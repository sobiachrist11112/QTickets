package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.EventData;

import java.util.List;

public class EventModel {
    @SerializedName("Data")
    @Expose
    public List<EventData> data = null;

}
