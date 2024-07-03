package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AllEventQT5Data implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("uniqueId")
    @Expose
    public String uniqueId;
    @SerializedName("eventTitle")
    @Expose
    public String eventTitle;
    @SerializedName("venue")
    @Expose
    public String venue;
    @SerializedName("countryId")
    @Expose
    public Integer countryId;
    @SerializedName("countryName")
    @Expose
    public String countryName;
    @SerializedName("categoryId")
    @Expose
    public Integer categoryId;
    @SerializedName("categoryName")
    @Expose
    public String categoryName;
    @SerializedName("isActive")
    @Expose
    public Boolean isActive;
    @SerializedName("eventIsFree")
    @Expose
    public Boolean eventIsFree;
    @SerializedName("eventIsOffline")
    @Expose
    public Boolean eventIsOffline;
    @SerializedName("isRecommended")
    @Expose
    public Boolean isRecommended;
    @SerializedName("isWebView")
    @Expose
    public Boolean isWebView;
    @SerializedName("webViewUrl")
    @Expose
    public String webViewUrl;
    @SerializedName("startDate")
    @Expose
    public String startDate;
    @SerializedName("endDate")
    @Expose
    public String endDate;
    @SerializedName("created_Date")
    @Expose
    public String created_Date;
    @SerializedName("banner")
    @Expose
    public String banner;
    @SerializedName("banner1")
    @Expose
    public String banner1;
    @SerializedName("banner2")
    @Expose
    public String banner2;
    @SerializedName("banner3")
    @Expose
    public String banner3;
    @SerializedName("banner4")
    @Expose
    public String banner4;
    @SerializedName("banner5")
    @Expose
    public String banner5;
    @SerializedName("banner6")
    @Expose
    public String banner6;
    @SerializedName("banner7")
    @Expose
    public String banner7;
    @SerializedName("banner8")
    @Expose
    public String banner8;

}
