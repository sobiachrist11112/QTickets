package com.production.qtickets.ticketbookingdetaile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.ArrayDataModel;

/**
 * Created by Harsh on 7/19/2018.
 */
public class NovoBookingConfirmationModel1 {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("array_data")
    @Expose
    public ArrayDataModel arrayData;
}
