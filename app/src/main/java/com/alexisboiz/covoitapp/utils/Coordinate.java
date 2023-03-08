package com.alexisboiz.covoitapp.utils;

public class Coordinate {
    private float lat;
    private float lon;

    public void setCoordinate(float lat, float lon){
        this.lat = lat;
        this.lon = lon;
    }

    public float getLat(){
        return lat;
    }

    public float getLon(){
        return lon;
    }
}
