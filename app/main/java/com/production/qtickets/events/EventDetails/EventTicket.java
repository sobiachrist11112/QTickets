package com.production.qtickets.events.EventDetails;

import android.os.Parcel;
import android.os.Parcelable;

public class EventTicket implements Parcelable {

    //tickets details based on the different fields
    public static final Creator<EventTicket> CREATOR = new Creator<EventTicket>() {
        @Override
        public EventTicket createFromParcel(Parcel in) {
            return new EventTicket(in);
        }

        @Override
        public EventTicket[] newArray(int size) {
            return new EventTicket[size];
        }
    };
    private final String ticketType;
    private final double rate;
    private final double serviceCharge;
    private int count;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    private int totalCount;

    public EventTicket(final String pTicketType, final double pRate, final  double pservice) {
        ticketType = pTicketType;
        rate = pRate;
        serviceCharge=pservice;
    }

    protected EventTicket(Parcel in) {
        ticketType = in.readString();
        rate = in.readDouble();
        count = in.readInt();
        totalCount = in.readInt();
        serviceCharge=in.readDouble();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ticketType);
        dest.writeDouble(rate);
        dest.writeInt(count);
        dest.writeInt(totalCount);
        dest.writeDouble(serviceCharge);

    }

    public String getTicketType() {
        return ticketType;
    }

    public double getRate() {
        return rate;
    }
    public  double getServiceCharge(){
        return serviceCharge;
    }
    public int getCount() {
        return count;
    }

    public void setCount(final int pCount) {
        count = pCount;
    }
}
