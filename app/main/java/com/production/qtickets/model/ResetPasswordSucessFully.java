package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordSucessFully {
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

        @SerializedName("userId")
        @Expose
        public Integer userId;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("password")
        @Expose
        public Object password;
        @SerializedName("confirmPassword")
        @Expose
        public Object confirmPassword;
        @SerializedName("otp")
        @Expose
        public Integer otp;
        @SerializedName("statusFlag")
        @Expose
        public Integer statusFlag;

    }


}
