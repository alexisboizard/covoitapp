package com.alexisboiz.covoitapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alexisboiz.covoitapp.carpool_area.CarpoolAreaFragment;
import com.alexisboiz.covoitapp.map.MapFragment;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;
import com.alexisboiz.covoitapp.notification.NotificationFragment;
import com.alexisboiz.covoitapp.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CarpoolAreaData carpoolAreaData;
    private final String API_URL = "https://public.opendatasoft.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bt_nav);
        bottomNavigationView.setSelectedItemId(R.id.home_nav);

        CarpoolAreaFragment carpoolAreaFragment = new CarpoolAreaFragment();
        MapFragment mapsFragment = new MapFragment();
        NotificationFragment notificationFragment = new NotificationFragment();
        SettingsFragment settingsFragment = new SettingsFragment();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home_nav:
                        requestAPI(API_URL, 10);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, carpoolAreaFragment).commit();
                        return true;
                    case R.id.map_nav:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, mapsFragment).commit();
                        return true;
                    case R.id.notification_nav:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, notificationFragment).commit();
                        return true;
                    case R.id.settings_nav:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, settingsFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }

    public void requestAPI(String api_url, int row_number) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Log.e("m", String.valueOf(retrofit));
        CarpoolService service = retrofit.create(CarpoolService.class);
        //https://public.opendatasoft.com/api/records/1.0/search/?dataset=aires-covoiturage&q=a&rows=1&facet=ville&facet=type_de_parking&facet=source&facet=pmr&facet=prix&facet=ouverture&facet=lumiere&facet=velo&facet=nom_reg&facet=nom_dep
        Map<String,String> queryParams = new HashMap<String,String>();
        queryParams.put("row", String.valueOf(row_number));
        queryParams.put("dataset", "aires-covoiturage");

        service.getCarpoolArea(queryParams).enqueue(new Callback<CarpoolAreaData>() {
            @Override
            public void onResponse(Call<CarpoolAreaData> call, Response<CarpoolAreaData> response) {
                if(response.isSuccessful()){
                    carpoolAreaData = response.body();
                    //Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(), "Error in API Request", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CarpoolAreaData> call, Throwable t) {
                Log.e("Error",String.valueOf(t));
            }
        });

    }
}

