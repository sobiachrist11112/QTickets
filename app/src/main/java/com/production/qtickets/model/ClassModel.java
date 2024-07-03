package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class ClassModel {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("cost")
    @Expose
    public String cost;
    @SerializedName("noOfRows")
    @Expose
    public String noOfRows;
    @SerializedName("Row")
    @Expose
    public List<Row> row = null;
}
