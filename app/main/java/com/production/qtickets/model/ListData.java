package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListData {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("heading")
    @Expose
    public String heading;
    @SerializedName("data")
    @Expose
    public List<Data> data;
}
