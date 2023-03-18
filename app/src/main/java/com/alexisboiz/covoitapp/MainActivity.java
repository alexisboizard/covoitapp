package com.alexisboiz.covoitapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.alexisboiz.covoitapp.carpool_area.CarpoolAreaFragment;
import com.alexisboiz.covoitapp.manager.CarpoolAreaDataManagerCallback;
import com.alexisboiz.covoitapp.manager.MainActivityController;
import com.alexisboiz.covoitapp.map.MapFragment;
import com.alexisboiz.covoitapp.model.API_Data.Record;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;
import com.alexisboiz.covoitapp.notification.NotificationFragment;
import com.alexisboiz.covoitapp.settings.SettingsFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {

    boolean APIRequestDone = false;

    BottomNavigationView bottomNavigationView;
    CarpoolAreaData carpoolAreaData;
    CarpoolAreaFragment carpoolAreaFragment;
    MapFragment mapsFragment;
    NotificationFragment notificationFragment;
    SettingsFragment settingsFragment;
    RelativeLayout loading_spinner;

    LatLng userLoc;

    MainActivity mainActivity;
    MainActivityController mainActivityController;

    public static int ROWS = 10;
    public final static String CARPOOL_AREA_DATA_KEY = "CARPOOL_AREA_DATA_KEY";
    public final static String USER_LOCATION_KEY = "USER_LOCATION_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        mainActivityController = new MainActivityController();

        boolean isNetworkAvailable = isNetworkAvailable(this);

        if(!isNetworkAvailable){
            Toast.makeText(this,"Network is not available", Toast.LENGTH_LONG).show();
        }

        loading_spinner = findViewById(R.id.loadingSpinner);

        bottomNavigationView = findViewById(R.id.bt_nav);
        bottomNavigationView.setSelectedItemId(R.id.home_nav);

        carpoolAreaFragment = new CarpoolAreaFragment();
        mapsFragment = new MapFragment();
        notificationFragment = new NotificationFragment();
        settingsFragment = new SettingsFragment();
        bottomNavigationView.setSelectedItemId(R.id.home_nav);

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        setUserLocation(fusedLocationClient);
        getApiData();



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        List<Record> r = carpoolAreaData.getRecords();
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

    private void setUserLocation(FusedLocationProviderClient fusedLocationClient) {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, true);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                userLoc = new LatLng(getLocation().getLatitude(),getLocation().getLongitude());

                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                userLoc = new LatLng(getLocation().getLatitude(),getLocation().getLongitude());
                            } else {
                                // (46.861588, 2.447208) point in center of France to center the map
                                userLoc = new LatLng(46.861588,2.447208);
                            }
                        }
                );

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    private Location getLocation() {
        LocationManager lm = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 5F, (LocationListener) this);
        }
        return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        userLoc = new LatLng(location.getLatitude(),location.getLongitude());
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void getApiData(){
        mainActivityController.getCarpoolAreaData(new CarpoolAreaDataManagerCallback() {
            @Override
            public void getCarpoolAreaResponseSuccess(CarpoolAreaData carpoolAreaData) {
                carpoolAreaFragment.init(carpoolAreaData, mapsFragment);
                Toast.makeText(getApplicationContext(), "API request Successful !", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, carpoolAreaFragment).commit();

                Bundle carpoolAreaFragmentBundle = new Bundle();
                carpoolAreaFragmentBundle.putParcelable(CARPOOL_AREA_DATA_KEY, carpoolAreaData);
                carpoolAreaFragment.setArguments(carpoolAreaFragmentBundle);


                Bundle mapsFragmentBundle = new Bundle();
                mapsFragmentBundle.putParcelable(CARPOOL_AREA_DATA_KEY, carpoolAreaData);
                mapsFragmentBundle.putParcelable(USER_LOCATION_KEY, userLoc);

                mapsFragment.setArguments(mapsFragmentBundle);

                loading_spinner.setVisibility(View.GONE);
            }

            @Override
            public void getCarpoolAreaResponseError(String error) {
                Log.e("getCarpoolAreaResponseError", error);
            }
        });
    }
}

