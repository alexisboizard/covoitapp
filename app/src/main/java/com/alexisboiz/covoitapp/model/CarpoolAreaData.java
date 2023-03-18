package com.alexisboiz.covoitapp.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.alexisboiz.covoitapp.model.API_Data.Fields;
import com.alexisboiz.covoitapp.model.API_Data.Parameters;
import com.alexisboiz.covoitapp.model.API_Data.Record;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class CarpoolAreaData implements Parcelable {

    @SerializedName("nhits")
    @Expose
    private Integer nhits;
    @SerializedName("parameters")
    @Expose
    private Parameters parameters;
    @SerializedName("records")
    @Expose
    private List<Record> records;

    protected CarpoolAreaData(Parcel in) {
        if (in.readByte() == 0) {
            nhits = null;
        } else {
            nhits = in.readInt();
        }
        parameters = in.readParcelable(Parameters.class.getClassLoader());
        records = in.createTypedArrayList(Record.CREATOR);
    }

    public static final Creator<CarpoolAreaData> CREATOR = new Creator<CarpoolAreaData>() {
        @Override
        public CarpoolAreaData createFromParcel(Parcel in) {
            return new CarpoolAreaData(in);
        }

        @Override
        public CarpoolAreaData[] newArray(int size) {
            return new CarpoolAreaData[size];
        }
    };

    public Integer getNhits() {
        return nhits;
    }

    public void setNhits(Integer nhits) {
        this.nhits = nhits;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public void append(CarpoolAreaData data){
        List<Record> tempRecords = this.getRecords();
        tempRecords.addAll(data.getRecords());
        this.setRecords(tempRecords);
    }

    public Fields getFieldsWithCoordinate(LatLng coordinate){
        for(Record record : records){
            Double lat = record.getFields().getCoordonnees().get(0);
            Double lng = record.getFields().getCoordonnees().get(1);

            LatLng itemCoord = new LatLng(lat,lng);

            if(itemCoord.latitude == coordinate.latitude && itemCoord.latitude == coordinate.latitude){
                return record.getFields();
            }
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(this.nhits);
        parcel.writeParcelable(this.parameters, i);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeParcelableList(this.records, i);
        }
    }
}