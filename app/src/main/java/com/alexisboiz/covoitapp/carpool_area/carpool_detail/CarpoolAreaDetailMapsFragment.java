package com.alexisboiz.covoitapp.carpool_area.carpool_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alexisboiz.covoitapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CarpoolAreaDetailMapsFragment extends Fragment {
    LatLng carpoolAreaLocation;
    String carpoolAreaTitle;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.addMarker(new MarkerOptions().position(carpoolAreaLocation).title(carpoolAreaTitle));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(carpoolAreaLocation));
            googleMap.setMaxZoomPreference(7.0f);
            googleMap.setMinZoomPreference(9.0f);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carpool_area_detail_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public void setCarpoolAreaLocation(Double lat, Double lng){
        carpoolAreaLocation = new LatLng(lat,lng);
    }
    public void setCarpoolAreaTitle(String title){
        carpoolAreaTitle = title;
    }
}