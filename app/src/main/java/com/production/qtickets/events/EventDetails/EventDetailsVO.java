package com.production.qtickets.events.EventDetails;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class EventDetailsVO implements Parcelable {

    public static final Creator<EventDetailsVO> CREATOR = new Creator<EventDetailsVO>() {

        public EventDetailsVO createFromParcel(Parcel in) {
            return new EventDetailsVO(in);
        }

        public EventDetailsVO[] newArray(int size) {
            return new EventDetailsVO[size];
        }

    };
    public int loacleventid;
    public String eventid, eventname, EDescription, startDate, endDate, endTime, StartTime, ContactPerson, ContactPersonEmail, ContactPersonNo, Latitude, Longitude, admission, alochol, EventUrl, thumbUrl, Venue, VDescription, EventQTurlPath, VenueId, thumbURL, bannerURL, posterURL, mobileURL, Category, CategoryId, SeatLayout, bookingType, EventType, categoryIcon, colorcode, bgcolorcode, bgbordercolorcode, btncolorcode, titlecolorcode, date_modified,showBrowser,eventListing;
    public ArrayList<EventTicketDetails> evntTktDetilas;

    public EventDetailsVO() {

    }

    public EventDetailsVO(Parcel in) {
        // TODO Auto-generated constructor stub
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        evntTktDetilas = new ArrayList<EventTicketDetails>();
        loacleventid = in.readInt();
        eventid = in.readString();
        eventname = in.readString();
        EDescription = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        endTime = in.readString();
        StartTime = in.readString();
        ContactPerson = in.readString();
        ContactPersonEmail = in.readString();
        ContactPersonNo = in.readString();
        Latitude = in.readString();
        Longitude = in.readString();
        admission = in.readString();
        alochol = in.readString();
        EventUrl = in.readString();
        thumbUrl = in.readString();
        Venue = in.readString();
        VDescription = in.readString();
        EventQTurlPath = in.readString();
        VenueId = in.readString();
        thumbURL = in.readString();
        bannerURL = in.readString();
        posterURL = in.readString();
        mobileURL = in.readString();
        Category = in.readString();
        CategoryId = in.readString();
        SeatLayout = in.readString();
        bookingType = in.readString();
        EventType = in.readString();
        categoryIcon = in.readString();
        colorcode = in.readString();
        bgcolorcode = in.readString();
        bgbordercolorcode = in.readString();
        btncolorcode = in.readString();
        titlecolorcode = in.readString();
        date_modified = in.readString();
        showBrowser=in.readString();
        eventListing=in.readString();
        in.readList(evntTktDetilas, getClass().getClassLoader());
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(loacleventid);
        dest.writeString(eventid);
        dest.writeString(eventname);
        dest.writeString(EDescription);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(endTime);
        dest.writeString(StartTime);
        dest.writeString(ContactPerson);
        dest.writeString(ContactPersonEmail);
        dest.writeString(ContactPersonNo);
        dest.writeString(Latitude);
        dest.writeString(Longitude);
        dest.writeString(admission);
        dest.writeString(alochol);
        dest.writeString(EventUrl);
        dest.writeString(thumbUrl);
        dest.writeString(Venue);
        dest.writeString(VDescription);
        dest.writeString(EventQTurlPath);
        dest.writeString(VenueId);
        dest.writeString(thumbURL);
        dest.writeString(bannerURL);
        dest.writeString(posterURL);
        dest.writeString(mobileURL);
        dest.writeString(Category);
        dest.writeString(CategoryId);
        dest.writeString(SeatLayout);
        dest.writeString(bookingType);
        dest.writeString(EventType);
        dest.writeString(categoryIcon);
        dest.writeString(colorcode);
        dest.writeString(bgcolorcode);
        dest.writeString(bgbordercolorcode);
        dest.writeString(btncolorcode);
        dest.writeString(titlecolorcode);
        dest.writeString(date_modified);
        dest.writeString(showBrowser);
        dest.writeString(eventListing);
        dest.writeList(evntTktDetilas);


    }
}
