package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordData {
    /*@SerializedName("data")
    @Expose
    public Data data;*/
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

/*
    public class Data {
        @SerializedName("userId")
        @Expose
        public Integer userId;
        @SerializedName("password")
        @Expose
        public Object password;
        @SerializedName("confirmPassword")
        @Expose
        public Object confirmPassword;

    }
*/
}
