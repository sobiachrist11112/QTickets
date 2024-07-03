package com.production.qtickets.model;

public class SelectedParentCount {

    int admitcount ;
    String ticketname ;

    public SelectedParentCount(int admitcount, String ticketname) {
        this.admitcount = admitcount;
        this.ticketname = ticketname;
    }

    public int getAdmitcount() {
        return admitcount;
    }

    public void setAdmitcount(int admitcount) {
        this.admitcount = admitcount;
    }

    public String getTicketname() {
        return ticketname;
    }

    public void setTicketname(String ticketname) {
        this.ticketname = ticketname;
    }
}
