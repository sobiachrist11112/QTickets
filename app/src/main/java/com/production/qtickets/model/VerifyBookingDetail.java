package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyBookingDetail {

    @SerializedName("isSuccess")
    @Expose
    public Boolean isSuccess;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Boolean data;


}
