package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerRatingTrailerStatistics {
    @SerializedName("RatingCount")
    @Expose
    public Integer ratingCount;
    @SerializedName("RatingCountLiked")
    @Expose
    public Integer ratingCountLiked;
}
