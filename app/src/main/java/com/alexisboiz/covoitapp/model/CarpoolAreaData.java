package com.alexisboiz.covoitapp.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

import com.alexisboiz.covoitapp.model.API_Data.Fields;
import com.alexisboiz.covoitapp.model.API_Data.Parameters;
import com.alexisboiz.covoitapp.model.API_Data.Record;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CarpoolAreaData implements Parcelable {

    @SerializedName("nhits")
    @Expose
    private Integer nhits;
    @SerializedName("parameters")
    @Expose
    private Parameters parameters;
    @SerializedName("records")
    @Expose
    private ObservableArrayList<Record> records;

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

    public CarpoolAreaData(){

    }

    protected CarpoolAreaData(Parcel in) {

    }

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

    public ObservableArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ObservableArrayList<Record> records) {
        this.records = records;
    }

    public void append(CarpoolAreaData data, boolean appendAtBegin){
        if(appendAtBegin){
            ObservableArrayList<Record> dataRecord = data.getRecords();
            dataRecord.addAll(this.getRecords());
            this.records.clear();
            this.setRecords(dataRecord);
        }else{
            ObservableArrayList<Record> tempRecords = this.getRecords();
            tempRecords.addAll(data.getRecords());
            //this.records.clear();
            this.setRecords(tempRecords);
        }

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

    public List<Record> find(CharSequence charSequence){
        List<Record> goodRecord = new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(charSequence);
        boolean isNumber = matcher.find();
        for(Record record : records){
            if(isNumber){
                String zip = record.getFields().getCodePostal();
                if(zip.contains(charSequence)){
                    goodRecord.add(record);
                }
            }else{
                String name = record.getFields().getNomDuLieu();
                if(name.contains(charSequence)){
                    goodRecord.add(record);
                }
            }
        }


        return goodRecord;
    }
    public ObservableArrayList<Record> findWithRecordId(List<String> recordIdList){
        ObservableArrayList<Record> goodRecords = new ObservableArrayList<>();
        for(String recordId : recordIdList){
            for(Record record : records){
                if(recordId.equals(record.getRecordid())){
                    goodRecords.add(record);
                }
            }
        }
        return goodRecords;
    }
    public Record findWithRecordId(String recordId){
        Record goodRecords = null;
            for(Record record : records){
                if(recordId.equals(record.getRecordid())){
                    goodRecords = record;
                }
            }
        return goodRecords;
    }


    public void moveAtTop(String recordId){
        Record givedRecord = findWithRecordId(recordId);
        records.add(0, givedRecord);
    }

    public void deleteDuplicate(){
        for (Record record : records){
            for (Record possibleDuplicateRecord : records){
                if(record.getRecordid().equals(possibleDuplicateRecord.getRecordid())){
                    records.remove(record);
                }
            }
        }
    }

    public void moveToFirst(Record record){
        int index = records.indexOf(record);
        records.remove(index);
        records.add(0, record);
    }
}