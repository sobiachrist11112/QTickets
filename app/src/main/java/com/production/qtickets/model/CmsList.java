package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CmsList {
    //serializing the name of the cms list
    @SerializedName("page_name")
    @Expose
    public String pageName;
    @SerializedName("page_url")
    @Expose
    public String pageUrl;
}
