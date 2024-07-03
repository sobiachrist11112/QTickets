package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerModel {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("items")
    @Expose
    public List<Item> items = null;
}
