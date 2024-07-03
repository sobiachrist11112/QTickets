package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Harsh on 7/13/2018.
 */
public class Rowlist {
    @SerializedName("physicalName")
    @Expose
    public String physicalName;
    @SerializedName("seatlist")
    @Expose
    public List<Seatlist> seatlist = null;
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
