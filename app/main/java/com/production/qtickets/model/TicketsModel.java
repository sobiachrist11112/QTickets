package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.TicketsData;

import java.util.List;



public class TicketsModel {
    @SerializedName("tickets")
    @Expose
    public List<TicketsData> data = null;
}
