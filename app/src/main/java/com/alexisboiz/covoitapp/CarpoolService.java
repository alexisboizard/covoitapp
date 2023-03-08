package com.alexisboiz.covoitapp;

import com.alexisboiz.covoitapp.model.CarpoolAreaData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CarpoolService {
    @GET("api/records/1.0/search/?")
    Call<CarpoolAreaData> getCarpoolArea(@QueryMap Map<String,String> dataset);
}
