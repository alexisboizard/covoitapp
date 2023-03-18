package com.alexisboiz.covoitapp.manager;

import com.alexisboiz.covoitapp.service.CarpoolService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    final static String BASE_URL = "https://public.opendatasoft.com/";

    private CarpoolService carpoolService = null;

    private static ApiManager instance;

    public CarpoolService getCarpoolService() {
        return carpoolService;
    }

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }

        return instance;
    }

    private ApiManager() {
        createRetrofitClock();
    }

    private void createRetrofitClock() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofitClock = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        carpoolService = retrofitClock.create(CarpoolService.class);
    }

}
