package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harsh on 7/13/2018.
 */
public class Seatlist {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("seatStyle")
    @Expose
    public String seatStyle;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("areaCode")
    @Expose
    public String areaCode;
    @SerializedName("styleType")
    @Expose
    public String styleType;
    @SerializedName("passingValue")
    @Expose
    public String passingValue;
    public  Integer ticketqty;
    public  Integer original_ticketqty;
    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public boolean ischecked;

    public Integer getMticket_type() {
        return mticket_type;
    }

    public void setMticket_type(Integer mticket_type) {
        this.mticket_type = mticket_type;
    }

    private Integer mticket_type;

}
