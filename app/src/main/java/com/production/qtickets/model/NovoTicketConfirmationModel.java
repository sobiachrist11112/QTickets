package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harsh on 7/20/2018.
 */
public class NovoTicketConfirmationModel {
    @SerializedName("user")
    @Expose
    public String user;
    @SerializedName("status")
    @Expose
    public String status;
}
