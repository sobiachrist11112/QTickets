package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetNewBannerEvents {

    @SerializedName("isSuccess")
    @Expose
    public Boolean isSuccess;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;

    public class Datum {

        @SerializedName("sn")
        @Expose
        public Integer sn;
        @SerializedName("banner")
        @Expose
        public String banner;
        @SerializedName("eventId")
        @Expose
        public Integer eventId;
        @SerializedName("eventTitle")
        @Expose
        public String eventTitle;
        @SerializedName("venue")
        @Expose
        public String venue;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("latitude")
        @Expose
        public String latitude;
        @SerializedName("longitude")
        @Expose
        public String longitude;
        @SerializedName("isWebView")
        @Expose
        public Boolean isWebView;
        @SerializedName("webViewUrl")
        @Expose
        public String webViewUrl;

    }
}
