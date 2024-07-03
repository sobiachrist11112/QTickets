package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllCategoryQT5Data {

    @SerializedName("id")
    @Expose
    public int id = 0;

    @SerializedName("name")
    @Expose
    public String name = "";

    @SerializedName("icon")
    @Expose
    public String icon = "";

}
