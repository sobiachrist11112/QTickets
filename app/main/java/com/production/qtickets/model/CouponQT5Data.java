package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponQT5Data {

    @SerializedName("id")
    @Expose
    public int id = 0;

    @SerializedName("name")
    @Expose
    public String name = "";

    @SerializedName("discountType")
    @Expose
    public int discountType = 0;

    @SerializedName("discount")
    @Expose
    public Double discount = 0.0;


}
