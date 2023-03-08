package com.alexisboiz.covoitapp.model.API_Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parameters {

    @SerializedName("dataset")
    @Expose
    private String dataset;
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

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
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

}