package com.alexisboiz.covoitapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alexisboiz.covoitapp.carpool_area.CarpoolAreaFragment;
import com.alexisboiz.covoitapp.map.MapFragment;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;
import com.alexisboiz.covoitapp.notification.NotificationFragment;
import com.alexisboiz.covoitapp.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private final String API_URL = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=aires-covoiturage&q=&rows=100000000&facet=ville&facet=type_de_parking&facet=source&facet=pmr&facet=transport_public&facet=prix&facet=ouverture&facet=lumiere&facet=velo&facet=couv4gbytel&facet=couv4gsfr&facet=couv4gorange&facet=couv4gfree&facet=nom_epci&facet=nom_reg&facet=nom_dep";

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
                        requestAPI(API_URL);
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

    public void requestAPI(String api_url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.e("m", String.valueOf(retrofit));
        CarpoolService service = retrofit.create(CarpoolService.class);
        service.getCarpoolArea("aires-covoiturage").enqueue(new Callback<CarpoolAreaData>() {
            @Override
            public void onResponse(Call<CarpoolAreaData> call, Response<CarpoolAreaData> response) {
                response.body();
            }

            @Override
            public void onFailure(Call<CarpoolAreaData> call, Throwable t) {
                Log.e("Error",String.valueOf(t));
            }
        });

    }

    public void getCarpoolArea(){

    }
}

