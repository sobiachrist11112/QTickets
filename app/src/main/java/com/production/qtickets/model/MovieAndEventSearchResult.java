package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieAndEventSearchResult {

    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
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

    public class Datum {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("type")
        @Expose
        public Integer type;
        @SerializedName("isWebVeiw")
        @Expose
        public Boolean isWebVeiw;
        @SerializedName("webViewUrl")
        @Expose
        public String webViewUrl;
        @SerializedName("path")
        @Expose
        public String path;




    }


}
