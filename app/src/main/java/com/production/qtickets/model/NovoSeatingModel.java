package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.Arealist;

import java.util.List;

/**
 * Created by Harsh on 7/13/2018.
 */
public class NovoSeatingModel {
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("bookingFee")
    @Expose
    public String bookingFee;
    @SerializedName("userSessionId")
    @Expose
    public String userSessionId;
    @SerializedName("maxTickets")
    @Expose
    public Integer maxTickets;
    @SerializedName("ratingPopup")
    @Expose
    public Integer ratingPopup;
    @SerializedName("ratingContent")
    @Expose
    public String ratingContent;
    @SerializedName("totalAmount")
    @Expose
    public Integer totalAmount;
    @SerializedName("handicapText")
    @Expose
    public String handicapText;
    @SerializedName("arealist")
    @Expose
    public List<Arealist> arealist = null;


}
