package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Harsh on 7/12/2018.
 */
public class NovoTicketSeletionModel {
    @SerializedName("maxTickets")
    @Expose
    public Integer maxTickets;
    @SerializedName("ticketlist")
    @Expose
    public List<TicketlistModel> ticketlist = null;
    @SerializedName("sevenStarTicketDescription")
    @Expose
    public String sevenStarTicketDescription;
}
