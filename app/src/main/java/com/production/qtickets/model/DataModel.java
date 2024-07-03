package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.EventData;

import java.util.List;

/**
 * Created by Harsh on 6/22/2018.
 */
public class DataModel {
    @SerializedName("Data")
    @Expose
    public List<EventData> data = null;
}
