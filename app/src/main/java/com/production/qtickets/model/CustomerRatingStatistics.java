package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerRatingStatistics {
    @SerializedName("RatingCount")
    @Expose
    public Integer ratingCount;
    @SerializedName("AverageScore")
    @Expose
    public Object averageScore;
}
