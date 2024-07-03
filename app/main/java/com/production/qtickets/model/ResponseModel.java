package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class ResponseModel {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("Classes")
    @Expose
    public SeatClassModel classes;
}
