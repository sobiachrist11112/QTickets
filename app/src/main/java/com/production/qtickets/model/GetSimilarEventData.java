package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetSimilarEventData {

    @SerializedName("isSuccess")
    @Expose
    public Boolean isSuccess;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public ArrayList<Datum> data;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("uniqueId")
        @Expose
        public String uniqueId;
        @SerializedName("eventTitle")
        @Expose
        public String eventTitle;
        @SerializedName("venue")
        @Expose
        public String venue;
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
        @SerializedName("isWebView")
        @Expose
        public Boolean isWebView;

        @SerializedName("webViewUrl")
        @Expose
        public String webViewUrl;

        @SerializedName("isRecommended")
        @Expose
        public Boolean isRecommended;
        @SerializedName("startDate")
        @Expose
        public String startDate;
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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public Boolean getWebView() {
            return isWebView;
        }

        public void setWebView(Boolean webView) {
            isWebView = webView;
        }

        public Boolean getRecommended() {
            return isRecommended;
        }

        public void setRecommended(Boolean recommended) {
            isRecommended = recommended;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
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
    }


}
