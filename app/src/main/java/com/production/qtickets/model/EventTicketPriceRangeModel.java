package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventTicketPriceRangeModel {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("items")
    @Expose
    public List<PriceRange> priceRangeList = null;
}
