package com.alexisboiz.covoitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.alexisboiz.covoitapp.map.MapFragment;
import com.alexisboiz.covoitapp.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bt_nav);
        bottomNavigationView.setSelectedItemId(R.id.home_nav);

        MapFragment mapsFragment = new MapFragment();
        SettingsFragment settingsFragment = new SettingsFragment();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home_nav:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, mapsFragment).commit();
                        return true;
                    case R.id.notification_nav:
                        return true;
                    case R.id.chat_nav:
                        return true;
                    case R.id.settings_nav:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, settingsFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}