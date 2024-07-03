package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventsFull implements Serializable {

    @SerializedName("eventid")
    @Expose
    public String eventid;
    @SerializedName("eventname")
    @Expose
    public String eventname;
    @SerializedName("EDescription")
    @Expose
    public String eDescription;
    @SerializedName("startDate")
    @Expose
    public String startDate;
    @SerializedName("endDate")
    @Expose
    public String endDate;
    @SerializedName("endTime")
    @Expose
    public String endTime;
    @SerializedName("StartTime")
    @Expose
    public String startTime;
    @SerializedName("ContactPerson")
    @Expose
    public String contactPerson;
    @SerializedName("ContactPersonEmail")
    @Expose
    public String contactPersonEmail;
    @SerializedName("ContactPersonNo")
    @Expose
    public String contactPersonNo;
    @SerializedName("Latitude")
    @Expose
    public String latitude;
    @SerializedName("Longitude")
    @Expose
    public String longitude;
    @SerializedName("admission")
    @Expose
    public String admission;
    @SerializedName("alochol")
    @Expose
    public String alochol;
    @SerializedName("EventUrl")
    @Expose
    public String eventUrl;
    @SerializedName("showBrowser")
    @Expose
    public String showBrowser;
    @SerializedName("EventListing")
    @Expose
    public String eventListing;
    @SerializedName("thumbUrl")
    @Expose
    public String thumbUrl;
    @SerializedName("Venue")
    @Expose
    public String venue;
    @SerializedName("VDescription")
    @Expose
    public String vDescription;
    @SerializedName("EventQTurlPath")
    @Expose
    public String eventQTurlPath;
    @SerializedName("VenueId")
    @Expose
    public String venueId;
    @SerializedName("bannerURL")
    @Expose
    public String bannerURL;
    @SerializedName("posterURL")
    @Expose
    public String posterURL;
    @SerializedName("mobileURL")
    @Expose
    public String mobileURL;
    @SerializedName("Category")
    @Expose
    public String category;
    @SerializedName("CategoryId")
    @Expose
    public String categoryId;
    @SerializedName("IsRedirect")
    @Expose
    public String isRedirect;
    @SerializedName("RedirectLink")
    @Expose
    public String redirectLink;
    @SerializedName("SeatLayout")
    @Expose
    public String seatLayout;
    @SerializedName("IsDtcm")
    @Expose
    public String isDtcm;
    @SerializedName("SingleSelectionTicketCategory")
    @Expose
    public String singleSelectionTicketCategory;
    @SerializedName("bookingType")
    @Expose
    public String bookingType;
    @SerializedName("Country")
    @Expose
    public String country;
    @SerializedName("Currency")
    @Expose
    public String currency;
    @SerializedName("EventType")
    @Expose
    public String eventType;
    @SerializedName("categoryIcon")
    @Expose
    public String categoryIcon;
    @SerializedName("colorcode")
    @Expose
    public String colorcode;
    @SerializedName("bgcolorcode")
    @Expose
    public String bgcolorcode;
    @SerializedName("bgbordercolorcode")
    @Expose
    public String bgbordercolorcode;
    @SerializedName("btncolorcode")
    @Expose
    public String btncolorcode;
    @SerializedName("titlecolorcode")
    @Expose
    public String titlecolorcode;
    @SerializedName("date_modified")
    @Expose
    public String dateModified;
    @SerializedName("viewname")
    @Expose
    public String viewname;
    private final static long serialVersionUID = -2165503866556341914L;
}
