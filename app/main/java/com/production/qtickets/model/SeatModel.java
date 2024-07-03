package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeatModel {
    @SerializedName("response")
    @Expose
    public ResponseModel response;

}
