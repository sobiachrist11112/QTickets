package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiesureData {

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


    @SerializedName("CityTourID")
    @Expose
    public Integer cityTourID;
    @SerializedName("TourName")
    @Expose
    public Object tourName;
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
    @SerializedName("imageTourUrls")
    @Expose
    public LiesureUrls imageTourUrls;


}
