package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harris on 30-05-2018.
 */

public class ChangePwdModel {

    //here we are serializing the fields which we are getting the response coming from the server

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
