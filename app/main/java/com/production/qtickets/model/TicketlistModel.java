package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Harsh on 7/12/2018.
 */
public class TicketlistModel implements Serializable {
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("areaCategoryCode")
    @Expose
    public String areaCategoryCode;
    @SerializedName("priceInCents")
    @Expose
    public Integer priceInCents;
    @SerializedName("ticketPrice")
    @Expose
    public String ticketPrice;
    @SerializedName("ticketTypeCode")
    @Expose
    public String ticketTypeCode;
    @SerializedName("ticketTypeDescription")
    @Expose
    public String ticketTypeDescription;
    @SerializedName("isValidforOffer")
    @Expose
    public Integer isValidforOffer;
    @SerializedName("currency")
    @Expose
    public String currency;
    public Integer ticketcount;
    public double totalamount;
    public int noOfSeats;

}
