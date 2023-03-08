package com.alexisboiz.covoitapp.model;

import com.alexisboiz.covoitapp.model.API_Data.Parameters;
import com.alexisboiz.covoitapp.model.API_Data.Record;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarpoolAreaData {

    @SerializedName("nhits")
    @Expose
    private Integer nhits;
    @SerializedName("parameters")
    @Expose
    private Parameters parameters;
    @SerializedName("records")
    @Expose
    private List<Record> records;

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

}