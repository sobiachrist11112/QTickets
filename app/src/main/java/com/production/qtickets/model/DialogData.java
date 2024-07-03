package com.production.qtickets.model;

// Define a model class for Gson to parse the JSON response
    public class DialogData {
        private String heading;
        private String description;
        private String posstive_btn;
        private String negative_btn;
        private String imgUrl;

        public String getHeading() {
            return heading;
        }

        public String getDescription() {
            return description;
        }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosstive_btn() {
        return posstive_btn;
    }

    public void setPosstive_btn(String posstive_btn) {
        this.posstive_btn = posstive_btn;
    }

    public String getNegative_btn() {
        return negative_btn;
    }

    public void setNegative_btn(String negative_btn) {
        this.negative_btn = negative_btn;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
            return imgUrl;
        }
    }