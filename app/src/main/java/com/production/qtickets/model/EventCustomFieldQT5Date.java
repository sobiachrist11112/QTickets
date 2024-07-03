package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventCustomFieldQT5Date {
    @SerializedName("id")
    @Expose
    public int id = 0;

    @SerializedName("eventId")
    @Expose
    public int eventId = 0;

    @SerializedName("formFor")
    @Expose
    public int formFor = 0;

    @SerializedName("isAdditional")
    @Expose
    public Boolean isAdditional = false;

    @SerializedName("name")
    @Expose
    public String name = "";

    @SerializedName("type")
    @Expose
    public int type = 0;

    @SerializedName("typeName")
    @Expose
    public String typeName = "";

    @SerializedName("length")
    @Expose
    public int length = 0;

    @SerializedName("isInclude")
    @Expose
    public Boolean isInclude = false;

    @SerializedName("isRequire")
    @Expose
    public Boolean isRequire = false;

    @SerializedName("options")
    @Expose
    public String options = "";

    @SerializedName("hasSubFields")
    @Expose
    public Boolean hasSubFields = false;

    @SerializedName("ticketXML")
    @Expose
    public String ticketXML = "";

    @SerializedName("fieldsXML")
    @Expose
    public String fieldsXML = "";

    @SerializedName("subFieldsXML")
    @Expose
    public String subFieldsXML = "";

    @SerializedName("ticketId")
    @Expose
    public int ticketId = 0;

    @SerializedName("ticketName")
    @Expose
    public String ticketName = "";

    @SerializedName("eventCustomFieldList")
    @Expose
    public List<EventCustomFieldListQT5> eventCustomFieldList = null;/*

    @SerializedName("eventCustomFieldOptionsList")
    @Expose
    public List<Object> eventCustomFieldOptionsList = null;*/

}
