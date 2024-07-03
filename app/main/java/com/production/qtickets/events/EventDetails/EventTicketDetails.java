package com.production.qtickets.events.EventDetails;

import android.os.Parcel;
import android.os.Parcelable;

public class EventTicketDetails implements Parcelable {
    //in details description of the event tickets

    public static final Creator<EventTicketDetails> CREATOR = new Creator<EventTicketDetails>() {

        public EventTicketDetails createFromParcel(Parcel in) {
            return new EventTicketDetails(in);
        }

        public EventTicketDetails[] newArray(int size) {
            return new EventTicketDetails[size];
        }

    };
    public int localevnettktid;
    public String tktpriceid, tktmasterid, TicketName, TotalTickets, Availability, ServiceCharge, TicketPrice, Admit, Date, NoOfTicketsPerTransaction, TicketType;

    public EventTicketDetails(Parcel in) {
        // TODO Auto-generated constructor stub
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {

        localevnettktid = in.readInt();
        tktpriceid = in.readString();
        tktmasterid = in.readString();
        TicketName = in.readString();
        TotalTickets = in.readString();
        Availability = in.readString();
        ServiceCharge = in.readString();
        TicketPrice = in.readString();
        Admit = in.readString();
        Date = in.readString();
        NoOfTicketsPerTransaction = in.readString();
        TicketType = in.readString();

    }

    public EventTicketDetails() {

    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(localevnettktid);
        dest.writeString(tktpriceid);
        dest.writeString(tktmasterid);
        dest.writeString(TicketName);
        dest.writeString(TotalTickets);
        dest.writeString(Availability);
        dest.writeString(ServiceCharge);
        dest.writeString(TicketPrice);
        dest.writeString(Admit);
        dest.writeString(Date);
        dest.writeString(NoOfTicketsPerTransaction);
        dest.writeString(TicketType);

    }
}
