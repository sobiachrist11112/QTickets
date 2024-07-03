package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.DateModel;

import java.util.List;

/**
 * Created by Harsh on 5/28/2018.
 */
public class ShowDateModel {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("dates")
    @Expose
    public List<DateModel> dates = null;
}
