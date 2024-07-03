package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.EventDetailFilterData;

import java.util.List;

public class EventDetailFilterModel {

    @SerializedName("Data")
    @Expose
    public List<EventDetailFilterData> data = null;
}
