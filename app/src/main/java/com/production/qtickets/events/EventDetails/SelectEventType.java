package com.production.qtickets.events.EventDetails;

import java.io.Serializable;

public class SelectEventType implements Serializable {

    //event ticket details class
    private  String tktpriceid;
    private  Integer count;
    private String eventMasterId;
    private  Integer position;

    public String getTktServicepriceid() {
        return tktServicepriceid;
    }

    public void setTktServicepriceid(String tktServicepriceid) {
        this.tktServicepriceid = tktServicepriceid;
    }

    private String tktServicepriceid;

    public String getEventMasterId() {
        return eventMasterId;
    }

    public void setEventMasterId(String eventMasterId) {
        this.eventMasterId = eventMasterId;
    }

    public String getTktpriceid() {
        return tktpriceid;
    }

    public void setTktpriceid(String tktpriceid) {
        this.tktpriceid = tktpriceid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
