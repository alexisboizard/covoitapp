package com.alexisboiz.covoitapp;

import com.alexisboiz.covoitapp.model.CarpoolAreaData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CarpoolService {
    @GET("api/records/1.0/search/?dataset")
    Call<CarpoolAreaData> getCarpoolArea(@Path("dataset") String dataset);
}
