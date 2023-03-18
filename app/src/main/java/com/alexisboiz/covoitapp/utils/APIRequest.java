package com.alexisboiz.covoitapp.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alexisboiz.covoitapp.MainActivity;
import com.alexisboiz.covoitapp.R;
import com.alexisboiz.covoitapp.carpool_area.CarpoolAreaFragment;
import com.alexisboiz.covoitapp.map.MapFragment;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;
import com.alexisboiz.covoitapp.service.CarpoolService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRequest {

    AppCompatActivity  appCompatActivity;
    private CarpoolAreaData carpoolAreaData;
    CarpoolAreaFragment carpoolAreaFragment;
    MapFragment mapsFragment;
    Context context;
    Activity activity;

    RelativeLayout loading_spinner;


    public APIRequest(Context context,Activity activity,AppCompatActivity  appCompatActivity,CarpoolAreaFragment carpoolAreaFragment, MapFragment mapFragment){
        this.context = context;
        this.activity = activity;
        this.appCompatActivity = appCompatActivity;
        this.carpoolAreaFragment = carpoolAreaFragment;
        this.mapsFragment = mapFragment;
        this.carpoolAreaFragment = new CarpoolAreaFragment();
        this.mapsFragment = new MapFragment();

    }
    public void requestAPI(String api_url, int row_number, int offset) {
        loading_spinner = appCompatActivity.findViewById(R.id.loadingSpinner);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CarpoolService service = retrofit.create(CarpoolService.class);

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("rows", String.valueOf(row_number));
        queryParams.put("dataset", "aires-covoiturage");
        queryParams.put("start", String.valueOf(offset));
        service.getCarpoolArea(queryParams).enqueue(new Callback<CarpoolAreaData>() {
            @Override
            public void onResponse(Call<CarpoolAreaData> call, Response<CarpoolAreaData> response) {
                if (response.isSuccessful()) {
                    carpoolAreaData = response.body();
                    loading_spinner.setVisibility(View.GONE);

                    Bundle carpoolAreaFragmentBundle = new Bundle();
                    carpoolAreaFragmentBundle.putParcelable(MainActivity.CARPOOL_AREA_DATA_KEY, carpoolAreaData);
                    carpoolAreaFragment.setArguments(carpoolAreaFragmentBundle);

                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, carpoolAreaFragment).commit();
                } else {
                    Toast.makeText(context, "API bad response...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CarpoolAreaData> call, Throwable t) {
                Toast.makeText(context, "API request failed...", Toast.LENGTH_LONG).show();
                Log.e("FAILED API REQ", String.valueOf(t));

            }
        });
    }

    public CarpoolAreaData getCarpoolAreaData(){
        return carpoolAreaData;
    }
}
