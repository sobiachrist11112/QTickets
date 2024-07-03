package com.production.qtickets.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CustomModel implements Parcelable {

    String heading;
    int size;

    public CustomModel(String heading, int size) {
        this.heading = heading;
        this.size = size;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    protected CustomModel(Parcel in) {
        heading = in.readString();
        size = in.readInt();
    }

    public static final Creator<CustomModel> CREATOR = new Creator<CustomModel>() {
        @Override
        public CustomModel createFromParcel(Parcel in) {
            return new CustomModel(in);
        }

        @Override
        public CustomModel[] newArray(int size) {
            return new CustomModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(heading);
        dest.writeInt(size);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
