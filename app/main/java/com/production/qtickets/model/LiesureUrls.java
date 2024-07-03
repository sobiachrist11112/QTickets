package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiesureUrls {
    @SerializedName("ImagePath")
    @Expose
    public String imagePath;
    @SerializedName("TourUrl")
    @Expose
    public String tourUrl;
}
