package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceRange {
    @SerializedName("minPrice")
    @Expose
    public String minPrice;
    @SerializedName("maxPrice")
    @Expose
    public String maxPrice;
}
