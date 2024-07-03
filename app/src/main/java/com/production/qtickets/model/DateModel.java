package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harsh on 5/28/2018.
 */
public class DateModel {
    public DateModel(String date) {
        this.date = date;
    }

    @SerializedName("date")
    @Expose
    public String date;

}
