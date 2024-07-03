package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WhatsAppChargeResponse {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("currency")
    @Expose
    public String currency;
}
