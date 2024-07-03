package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.EventFilterData;

import java.util.List;

public class EventFilterModel {
    @SerializedName("Data")
    @Expose
    public List<EventFilterData> data = null;
}
