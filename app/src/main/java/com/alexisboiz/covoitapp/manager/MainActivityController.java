package com.alexisboiz.covoitapp.manager;

import android.util.Log;

import com.alexisboiz.covoitapp.MainActivity;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityController {

    private final ApiManager apiManager;
    private Map<String,String> parameters;

    public MainActivityController() {
        apiManager = ApiManager.getInstance();
        parameters = new HashMap<>();
        parameters.put("rows", String.valueOf(MainActivity.ROWS));
        parameters.put("dataset", "aires-covoiturage");

    }

    public void getCarpoolAreaData(CarpoolAreaDataManagerCallback callback){
        Call<CarpoolAreaData> call = apiManager.getCarpoolService().getCarpoolArea(parameters);
        call.enqueue(new Callback<CarpoolAreaData>() {
            @Override
            public void onResponse(Call<CarpoolAreaData> call, Response<CarpoolAreaData> response) {
                if(response.isSuccessful()){

                    CarpoolAreaData tempData = response.body();
                    CarpoolAreaData carpoolAreaData = CarpoolAreaData.getInstance();
                    carpoolAreaData.setRecords(tempData.getRecords());
                    carpoolAreaData.setNhits(tempData.getNhits());
                    carpoolAreaData.setParameters(tempData.getParameters());

                    callback.getCarpoolAreaResponseSuccess(carpoolAreaData);
                }else{
                    Log.e("onResponse", "Not successfull : " + response.code());
                    callback.getCarpoolAreaResponseError("Erreur le serveur a repondu status : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CarpoolAreaData> call, Throwable t) {
                Log.e("onFailure", t.getLocalizedMessage());
                callback.getCarpoolAreaResponseError("Erreur lors de la requete : " + t.getLocalizedMessage());

            }
        });
    }
}

