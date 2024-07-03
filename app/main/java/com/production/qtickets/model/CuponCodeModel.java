package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CuponCodeModel {

    @SerializedName("data")
    @Expose
    public Data data;
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


    public class Data {
        @SerializedName("voucherAmount")
        @Expose
        public Integer voucherAmount;
        @SerializedName("voucherName")
        @Expose
        public String voucherName;

    }
}
