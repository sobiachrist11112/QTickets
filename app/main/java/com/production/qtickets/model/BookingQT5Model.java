package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingQT5Model {

    @SerializedName("status")
    @Expose
    public String status = "";

    @SerializedName("isSuccess")
    @Expose
    public Boolean isSuccess = false;

    @SerializedName("statusCode")
    @Expose
    public String statusCode = "";

    @SerializedName("message")
    @Expose
    public String message = "";

    @SerializedName("data")
    @Expose
    public BookingQT5Data data = null;

}
