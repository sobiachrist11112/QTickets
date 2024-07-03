package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harris on 05-06-2018.
 */

public class VerifyMobileModel {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("errorcode")
    @Expose
    public String errorcode;
    @SerializedName("errormsg")
    @Expose
    public String errormsg;
}
