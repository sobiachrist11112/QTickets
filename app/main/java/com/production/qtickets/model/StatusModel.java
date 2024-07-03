package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatusModel {
    //serializing the name of the cms list
    @SerializedName("Status")
    @Expose
    public String status;
    @SerializedName("Cms_list")
    @Expose
    public List<CmsList> cmsList = null;
}
