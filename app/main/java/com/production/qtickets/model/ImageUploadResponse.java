package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageUploadResponse {

    @SerializedName("status")
    @Expose
    public Object status;
    @SerializedName("isSuccess")
    @Expose
    public Boolean isSuccess;
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public Object message;
    @SerializedName("data")
    @Expose
    public String data;

}
