package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenModel {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("RetMsg")
    @Expose
    public String RetMsg;
}
