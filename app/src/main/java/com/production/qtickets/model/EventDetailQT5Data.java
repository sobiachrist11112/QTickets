package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EventDetailQT5Data {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("mseoid")
    @Expose
    public Integer mseoid;
    @SerializedName("uniqueId")
    @Expose
    public String uniqueId;
    @SerializedName("eventTitle")
    @Expose
    public String eventTitle;
    @SerializedName("venue")
    @Expose
    public String venue;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("countryId")
    @Expose
    public Integer countryId;
    @SerializedName("countryName")
    @Expose
    public String countryName;
    @SerializedName("categoryId")
    @Expose
    public Integer categoryId;
    @SerializedName("categoryName")
    @Expose
    public String categoryName;
    @SerializedName("isActive")
    @Expose
    public Boolean isActive;
    @SerializedName("eventIsFree")
    @Expose
    public Boolean eventIsFree;
    @SerializedName("eventIsOffline")
    @Expose
    public Boolean eventIsOffline;
    @SerializedName("startDate")
    @Expose
    public String startDate;
    @SerializedName("startTime")
    @Expose
    public String startTime;
    @SerializedName("endDate")
    @Expose
    public String endDate;
    @SerializedName("created_Date")
    @Expose
    public String created_Date;
    @SerializedName("banner")
    @Expose
    public String banner;
    @SerializedName("banner1")
    @Expose
    public String banner1;
    @SerializedName("banner2")
    @Expose
    public String banner2;
    @SerializedName("banner3")
    @Expose
    public String banner3;
    @SerializedName("banner4")
    @Expose
    public String banner4;
    @SerializedName("banner5")
    @Expose
    public String banner5;
    @SerializedName("banner6")
    @Expose
    public String banner6;
    @SerializedName("banner7")
    @Expose
    public String banner7;
    @SerializedName("banner8")
    @Expose
    public String banner8;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;
    @SerializedName("eventGalleryImages")
    @Expose
    public List<String> eventGalleryImages = null;
    @SerializedName("eventDescription")
    @Expose
    public String eventDescription;
    @SerializedName("faq")
    @Expose
    public String faq;
    @SerializedName("metaTitle")
    @Expose
    public String metaTitle;
    @SerializedName("metaDescription")
    @Expose
    public String metaDescription;
    @SerializedName("metaKeywords")
    @Expose
    public String metaKeywords;
    @SerializedName("metaTags")
    @Expose
    public String metaTags;
    @SerializedName("metaURL")
    @Expose
    public Object metaURL;
    @SerializedName("isSeatLayoutRequired")
    @Expose
    public Integer isSeatLayoutRequired;
    @SerializedName("layoutFile")
    @Expose
    public String layoutFile;
    @SerializedName("isParkingAllow")
    @Expose
    public Integer isParkingAllow;
    @SerializedName("isSmokingAllow")
    @Expose
    public Integer isSmokingAllow;
    @SerializedName("isCameraAvailable")
    @Expose
    public Integer isCameraAvailable;
    @SerializedName("isFBAllow")
    @Expose
    public Integer isFBAllow;
    @SerializedName("isCasualAllow")
    @Expose
    public Integer isCasualAllow;
    @SerializedName("isRecommended")
    @Expose
    public Integer isRecommended;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("is_PopUp")
    @Expose
    public String is_PopUp;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("willAlcoholServed")
    @Expose
    public String willAlcoholServed;
    @SerializedName("is_WebView")
    @Expose
    public String is_WebView;

    @SerializedName("webViewUrl")
    @Expose
    public String webViewUrl;

    @SerializedName("eventSocialLinks")
    @Expose
    public List<EventSocialLink> eventSocialLinks = null;
    @SerializedName("videoLinks")
    @Expose
    public List<String> videoLinks = null;
    @SerializedName("admission")
    @Expose
    public String admission;

    @SerializedName("eventUrl")
    @Expose
    public String eventUrl;


    public String getWebViewUrl() {
        return webViewUrl;
    }

    public void setWebViewUrl(String webViewUrl) {
        this.webViewUrl = webViewUrl;
    }

    public List<EventSocialLink> getEventSocialLinks() {
        return eventSocialLinks;
    }

    public void setEventSocialLinks(List<EventSocialLink> eventSocialLinks) {
        this.eventSocialLinks = eventSocialLinks;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMseoid() {
        return mseoid;
    }

    public void setMseoid(Integer mseoid) {
        this.mseoid = mseoid;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getEventIsFree() {
        return eventIsFree;
    }

    public void setEventIsFree(Boolean eventIsFree) {
        this.eventIsFree = eventIsFree;
    }

    public Boolean getEventIsOffline() {
        return eventIsOffline;
    }

    public void setEventIsOffline(Boolean eventIsOffline) {
        this.eventIsOffline = eventIsOffline;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreated_Date() {
        return created_Date;
    }

    public void setCreated_Date(String created_Date) {
        this.created_Date = created_Date;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBanner1() {
        return banner1;
    }

    public void setBanner1(String banner1) {
        this.banner1 = banner1;
    }

    public String getBanner2() {
        return banner2;
    }

    public void setBanner2(String banner2) {
        this.banner2 = banner2;
    }

    public String getBanner3() {
        return banner3;
    }

    public void setBanner3(String banner3) {
        this.banner3 = banner3;
    }

    public String getBanner4() {
        return banner4;
    }

    public void setBanner4(String banner4) {
        this.banner4 = banner4;
    }

    public String getBanner5() {
        return banner5;
    }

    public void setBanner5(String banner5) {
        this.banner5 = banner5;
    }

    public String getBanner6() {
        return banner6;
    }

    public void setBanner6(String banner6) {
        this.banner6 = banner6;
    }

    public String getBanner7() {
        return banner7;
    }

    public void setBanner7(String banner7) {
        this.banner7 = banner7;
    }

    public String getBanner8() {
        return banner8;
    }

    public void setBanner8(String banner8) {
        this.banner8 = banner8;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<String> getEventGalleryImages() {
        return eventGalleryImages;
    }

    public void setEventGalleryImages(List<String> eventGalleryImages) {
        this.eventGalleryImages = eventGalleryImages;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getFaq() {
        return faq;
    }

    public void setFaq(String faq) {
        this.faq = faq;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaTags() {
        return metaTags;
    }

    public void setMetaTags(String metaTags) {
        this.metaTags = metaTags;
    }

    public Object getMetaURL() {
        return metaURL;
    }

    public void setMetaURL(Object metaURL) {
        this.metaURL = metaURL;
    }

    public Integer getIsSeatLayoutRequired() {
        return isSeatLayoutRequired;
    }

    public void setIsSeatLayoutRequired(Integer isSeatLayoutRequired) {
        this.isSeatLayoutRequired = isSeatLayoutRequired;
    }

    public String getLayoutFile() {
        return layoutFile;
    }

    public void setLayoutFile(String layoutFile) {
        this.layoutFile = layoutFile;
    }

    public Integer getIsParkingAllow() {
        return isParkingAllow;
    }

    public void setIsParkingAllow(Integer isParkingAllow) {
        this.isParkingAllow = isParkingAllow;
    }

    public Integer getIsSmokingAllow() {
        return isSmokingAllow;
    }

    public void setIsSmokingAllow(Integer isSmokingAllow) {
        this.isSmokingAllow = isSmokingAllow;
    }

    public Integer getIsCameraAvailable() {
        return isCameraAvailable;
    }

    public void setIsCameraAvailable(Integer isCameraAvailable) {
        this.isCameraAvailable = isCameraAvailable;
    }

    public Integer getIsFBAllow() {
        return isFBAllow;
    }

    public void setIsFBAllow(Integer isFBAllow) {
        this.isFBAllow = isFBAllow;
    }

    public Integer getIsCasualAllow() {
        return isCasualAllow;
    }

    public void setIsCasualAllow(Integer isCasualAllow) {
        this.isCasualAllow = isCasualAllow;
    }

    public Integer getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(Integer isRecommended) {
        this.isRecommended = isRecommended;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getIs_PopUp() {
        return is_PopUp;
    }

    public void setIs_PopUp(String is_PopUp) {
        this.is_PopUp = is_PopUp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWillAlcoholServed() {
        return willAlcoholServed;
    }

    public void setWillAlcoholServed(String willAlcoholServed) {
        this.willAlcoholServed = willAlcoholServed;
    }

    public String getIs_WebView() {
        return is_WebView;
    }

    public void setIs_WebView(String is_WebView) {
        this.is_WebView = is_WebView;
    }

    public List<String> getVideoLinks() {
        return videoLinks;
    }

    public void setVideoLinks(List<String> videoLinks) {
        this.videoLinks = videoLinks;
    }

    public String getAdmission() {
        return admission;
    }

    public void setAdmission(String admission) {
        this.admission = admission;
    }
}
