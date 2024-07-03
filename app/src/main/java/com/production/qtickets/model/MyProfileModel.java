package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyProfileModel {
    @SerializedName("verify")
    @Expose
    public String verify;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("nationality")
    @Expose
    public String nationality;
    @SerializedName("mobileno")
    @Expose
    public String mobileno;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("userid")
    @Expose
    public String userid;
}
