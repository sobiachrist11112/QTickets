package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllCountryQT5Data {

    @SerializedName("id")
    @Expose
    public int id = 0;

    @SerializedName("name")
    @Expose
    public String name = "";

    @SerializedName("isdCode")
    @Expose
    public String isdCode = "";

    @SerializedName("codeAlpha2")
    @Expose
    public String codeAlpha2 = "";

    @SerializedName("codeAlpha3")
    @Expose
    public String codeAlpha3 = "";

    @SerializedName("currencyCode")
    @Expose
    public String currencyCode = "";

    @SerializedName("nationality")
    @Expose
    public String nationality = "";

    @SerializedName("flag")
    @Expose
    public String flag = "";

    @Override
    public String toString() {
        return name;
    }
}
