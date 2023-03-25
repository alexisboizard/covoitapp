package com.alexisboiz.covoitapp.model.API_Data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parameters implements Parcelable {

    @SerializedName("dataset")
    @Expose
    private String dataset;
    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("rows")
    @Expose
    private Integer rows;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("timezone")
    @Expose
    private String timezone;

    protected Parameters(Parcel in) {
        dataset = in.readString();
        q = in.readString();
        if (in.readByte() == 0) {
            rows = null;
        } else {
            rows = in.readInt();
        }
        if (in.readByte() == 0) {
            start = null;
        } else {
            start = in.readInt();
        }
        format = in.readString();
        timezone = in.readString();
    }

    public static final Creator<Parameters> CREATOR = new Creator<Parameters>() {
        @Override
        public Parameters createFromParcel(Parcel in) {
            return new Parameters(in);
        }

        @Override
        public Parameters[] newArray(int size) {
            return new Parameters[size];
        }
    };

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(this.dataset);
        parcel.writeString(this.q);
        parcel.writeInt(this.rows);
        parcel.writeInt(this.start);
        parcel.writeString(this.format);
        parcel.writeString(this.timezone);
    }
}