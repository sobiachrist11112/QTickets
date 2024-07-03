package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class Row {
    @SerializedName("letter")
    @Expose
    public String letter;
    @SerializedName("noOfSeats")
    @Expose
    public String noOfSeats;
    @SerializedName("availableCount")
    @Expose
    public String availableCount;
    @SerializedName("Availableseats")
    @Expose
    public String availableseats;
    @SerializedName("isFamily")
    @Expose
    public String isFamily;
    @SerializedName("gangwaySeats")
    @Expose
    public String gangwaySeats;
    @SerializedName("gangwayCounts")
    @Expose
    public String gangwayCounts;
    @SerializedName("AllSeats")
    @Expose
    public String allSeats;
    @SerializedName("isGangway")
    @Expose
    public String isGangway;
    @SerializedName("isInitialGangway")
    @Expose
    public String isInitialGangway;
    @SerializedName("isInitialGangwayCount")
    @Expose
    public String isInitialGangwayCount;
    public String getmCost() {
        return mCost;
    }

    public void setmCost(String mCost) {
        this.mCost = mCost;
    }
    public void setmName(String mName) {
        this.mName = mName;
    }
    public String getmName(){return mName;}

    private String mCost;
    private String mName;

}
