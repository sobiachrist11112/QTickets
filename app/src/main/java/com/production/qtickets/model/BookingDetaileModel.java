package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harsh on 6/4/2018.
 */
public class BookingDetaileModel {
    //block seat
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("Transaction_Id")
    @Expose
    public String transactionId;
    @SerializedName("PageSessionTime")
    @Expose
    public String pageSessionTime;
    @SerializedName("TransactionTime")
    @Expose
    public String transactionTime;
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("ticketprice")
    @Expose
    public String ticketprice;
    @SerializedName("servicecharges")
    @Expose
    public String servicecharges;
    @SerializedName("totalprice")
    @Expose
    public String totalprice;

    //seat lock
    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("retstr")
    @Expose
    public String retstr;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("verify")
    @Expose
    public String verify;
    @SerializedName("errorcode")
    @Expose
    public String errorcode;
    @SerializedName("errormsg")
    @Expose
    public String errormsg;
    @SerializedName("requestedtimeinsec")
    @Expose
    public String requestedtimeinsec;
    @SerializedName("timedoutinmins")
    @Expose
    public String timedoutinmins;

    //apply couponcode
    @SerializedName("coupon")
    @Expose
    public String coupon;
    @SerializedName("couponCode")
    @Expose
    public String couponCode;
    @SerializedName("balance")
    @Expose
    public String balance;

    //seat confirmation
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("OrderInfo")
    @Expose
    public String orderInfo;

    //event ticket booking detailes
    @SerializedName("orderid")
    @Expose
    public String orderid;
    @SerializedName("balanceamount")
    @Expose
    public String balanceamount;
}
