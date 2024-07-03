package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.ItemModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Harsh on 5/29/2018.
 */
public class MallBydateModel implements Serializable{
    @SerializedName("items")
    @Expose
    public List<ItemModel> items = null;
    @SerializedName("delay")
    @Expose
    public List<Object> delay = null;
    @SerializedName("movie_name")
    @Expose
    public String movieName;
    @SerializedName("city_name")
    @Expose
    public String cityName;
    @SerializedName("shareURL")
    @Expose
    public String shareURL;
    /*novo username and password*/
    @SerializedName("un")
    @Expose
    public String un;
    @SerializedName("pw")
    @Expose
    public String pw;

}
