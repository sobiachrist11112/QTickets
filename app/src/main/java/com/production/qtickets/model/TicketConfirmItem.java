package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.production.qtickets.model.TicketConfirmationMovieModel;

import java.util.List;

/**
 * Created by Harsh on 6/27/2018.
 */
public class TicketConfirmItem {
    @SerializedName("items")
    @Expose
    public List<TicketConfirmationMovieModel> items = null;
}
