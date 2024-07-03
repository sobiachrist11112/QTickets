package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by Harsh on 5/21/2018.
 */
public class DashboardModel {
    //serialization of fields in android

    @SerializedName("StatusCode")
    @Expose
    public String statusCode;
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("categories")
    @Expose
    public List<CategoryModel> categories = null;
    @SerializedName("movies")
    @Expose
    public List<MovieModel> movies = null;
    @SerializedName("Events")
    @Expose
    public List<EventData> events = null;
    @SerializedName("Sports")
    @Expose
    public List<EventData> sports = null;
    @SerializedName("Leisure")
    @Expose
    public List<EventData> leisure = null;
    @SerializedName("movieURL")
    @Expose
    public String movieURL;
    @SerializedName("eventURL")
    @Expose
    public String eventURL;
    @SerializedName("listData")
    @Expose
    public List<ListData> listdata;
}
