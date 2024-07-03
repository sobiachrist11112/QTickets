package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoCodeData {

    @SerializedName("promoCodeName")
    @Expose
    public String promoCodeName;
    @SerializedName("discount")
    @Expose
    public Integer discount;

}
