package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventData {
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
    @SerializedName("SeatLayout")
    @Expose
    public String seatLayout;
    @SerializedName("bookingType")
    @Expose
    public String bookingType;
    @SerializedName("Country")
    @Expose
    public String country;
    @SerializedName("EventType")
    @Expose
    public String eventType;
    @SerializedName("categoryIcon")
    @Expose
    public String categoryIcon;
    @SerializedName("date_modified")
    @Expose
    public String dateModified;



    //leisureNew api
    @SerializedName("totalCount")
    @Expose
    public Integer totalCount;
    @SerializedName("pageSize")
    @Expose
    public Integer pageSize;
    @SerializedName("currentPage")
    @Expose
    public Integer currentPage;
    @SerializedName("totalPages")
    @Expose
    public Integer totalPages;
    @SerializedName("previousPage")
    @Expose
    public String previousPage;
    @SerializedName("nextPage")
    @Expose
    public String nextPage;

    @SerializedName("imageTourUrls")
    @Expose
    public LiesureUrls imageTourUrls;

    @SerializedName("CityTourID")
    @Expose
    public Integer cityTourID;
    @SerializedName("TourName")
    @Expose
    public String tourName;
    @SerializedName("CountryId")
    @Expose
    public Integer countryId;
    @SerializedName("CityId")
    @Expose
    public Integer cityId;
    @SerializedName("ChildFromAge")
    @Expose
    public Integer childFromAge;
    @SerializedName("ChildToAge")
    @Expose
    public Integer childToAge;
    @SerializedName("InfantFromAge")
    @Expose
    public Integer infantFromAge;
    @SerializedName("InfantToAge")
    @Expose
    public Integer infantToAge;
    @SerializedName("CityTourTypeId")
    @Expose
    public Integer cityTourTypeId;
    @SerializedName("IsComboTour")
    @Expose
    public Boolean isComboTour;
    @SerializedName("IsTimeSlot")
    @Expose
    public Boolean isTimeSlot;
    @SerializedName("SpecialNoteWF")
    @Expose
    public Object specialNoteWF;
    @SerializedName("CancellationPolicyID")
    @Expose
    public Integer cancellationPolicyID;
    @SerializedName("UsefulInformationID")
    @Expose
    public Integer usefulInformationID;
    @SerializedName("TermsAndConditionID")
    @Expose
    public Integer termsAndConditionID;
    @SerializedName("FAQCategoryID")
    @Expose
    public Integer fAQCategoryID;
    @SerializedName("CityTourType")
    @Expose
    public String cityTourType;
    @SerializedName("ImportantInformation")
    @Expose
    public Object importantInformation;
    @SerializedName("Duration")
    @Expose
    public String duration;



}
