package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReelCinemaMovieListModel {
    @SerializedName("result")
    @Expose
    public Integer result;
    @SerializedName("data")
    @Expose
    public MetaData data;

}
