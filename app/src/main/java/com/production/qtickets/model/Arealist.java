package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Harsh on 7/13/2018.
 */
public class Arealist {
    @SerializedName("areaCode")
    @Expose
    public String areaCode;
    @SerializedName("areaDescription")
    @Expose
    public String areaDescription;
    @SerializedName("ticketTypeCode")
    @Expose
    public String ticketTypeCode;
    @SerializedName("priceGroupCode")
    @Expose
    public String priceGroupCode;
    @SerializedName("ticketPrice")
    @Expose
    public Integer ticketPrice;
    @SerializedName("mashreqOfferPrice")
    @Expose
    public Object mashreqOfferPrice;
    @SerializedName("ticketTypeDescription")
    @Expose
    public String ticketTypeDescription;
    @SerializedName("selectedTicketQty")
    @Expose
    public Integer selectedTicketQty;
    @SerializedName("rowlist")
    @Expose
    public List<Rowlist> rowlist = null;

    public Integer getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(Integer ticket_type) {
        this.ticket_type = ticket_type;
    }

    private Integer ticket_type;

}
