package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSublistview {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("isSuccess")
    @Expose
    public Boolean isSuccess;
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<CustomCHild> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
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

    public List<CustomCHild> getData() {
        return data;
    }

    public void setData(List<CustomCHild> data) {
        this.data = data;
    }

    public class CustomCHild {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("fieldOptionsId")
        @Expose
        public Integer fieldOptionsId;
        @SerializedName("is_Require")
        @Expose
        public Boolean is_Require;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("type")
        @Expose
        public Integer type;
        @SerializedName("typeName")
        @Expose
        public String typeName;
        @SerializedName("length")
        @Expose
        public Integer length;
        @SerializedName("options")
        @Expose
        public String options;
        @SerializedName("value")
        @Expose
        public Integer value;
        @SerializedName("text")
        @Expose
        public String text;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getFieldOptionsId() {
            return fieldOptionsId;
        }

        public void setFieldOptionsId(Integer fieldOptionsId) {
            this.fieldOptionsId = fieldOptionsId;
        }

        public Boolean getIs_Require() {
            return is_Require;
        }

        public void setIs_Require(Boolean is_Require) {
            this.is_Require = is_Require;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public String getOptions() {
            return options;
        }

        public void setOptions(String options) {
            this.options = options;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

}
