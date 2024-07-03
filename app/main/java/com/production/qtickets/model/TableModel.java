package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harsh on 7/19/2018.
 */
public class TableModel {
    @SerializedName("bookid")
    @Expose
    public Integer bookid;
    @SerializedName("amount")
    @Expose
    public Integer amount;
    @SerializedName("serch")
    @Expose
    public Integer serch;
    @SerializedName("seats")
    @Expose
    public String seats;
    @SerializedName("Column1")
    @Expose
    public String Column1;

}
