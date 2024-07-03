/*
package com.production.qtickets.events.EventDetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.QticketsApplication;

import java.util.ArrayList;
import java.util.List;

public class EventBookingInfo implements Parcelable {


    // for storing the ticket details and prices we used this class

    String eventId = QTUtils.getInstance().getFromSharedPreference(QticketsApplication.getInstance(), AppConstants.EVENT_ID);
    String boat_type = QTUtils.getInstance().getFromSharedPreference(QticketsApplication.getInstance(), AppConstants.BOAT_TYPE);


    public static final Creator<EventBookingInfo> CREATOR = new Creator<EventBookingInfo>() {
        @Override
        public EventBookingInfo createFromParcel(Parcel in) {
            return new EventBookingInfo(in);
        }

        @Override
        public EventBookingInfo[] newArray(int size) {
            return new EventBookingInfo[size];
        }
    };
    private final List<EventTicket> mEventTickets;

    public EventBookingInfo() {
        mEventTickets = new ArrayList<>();
    }

    protected EventBookingInfo(Parcel in) {
        mEventTickets = in.createTypedArrayList(EventTicket.CREATOR);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mEventTickets);
    }

    public void addEventTicket(final EventTicket pEventTicket) {
        mEventTickets.add(pEventTicket);
    }

    public int getTotalTicketsCount() {
        int totalTicketsCount = 0;
        for (final EventTicket cost : mEventTickets) {
            final int count = cost.getTotalCount();
            totalTicketsCount += count;
        }
        return totalTicketsCount;
    }

    public double getTotalCost() {
        double totalCost = 0;
        for (final EventTicket cost : mEventTickets) {
            if (Integer.parseInt(eventId) == 422) {
                totalCost += cost.getRate() * 1;
            } else if ((Integer.parseInt(eventId) == 421)) {
                if (boat_type != null && boat_type.equalsIgnoreCase("3")) {
                    totalCost += cost.getRate() * 1;
                } else {

                }

            } else {
                totalCost += cost.getRate() * cost.getCount();
            }

        }
        return totalCost;
    }

    public double getTotalServiceCost() {
        double serviceCost = 0;
        for (final EventTicket cost : mEventTickets) {
            if (Integer.parseInt(eventId) == 422) {
                serviceCost += cost.getServiceCharge() * 1;
            } else if ((Integer.parseInt(eventId) == 421)) {
                if (boat_type != null && boat_type.equalsIgnoreCase("3")) {
                    serviceCost += cost.getServiceCharge() * 1;
                } else {

                }

            } else {
                serviceCost += cost.getServiceCharge() * cost.getCount();
            }

        }
        return serviceCost;
    }
}
*/
