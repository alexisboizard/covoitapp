package com.alexisboiz.covoitapp.map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alexisboiz.covoitapp.MainActivity;
import com.alexisboiz.covoitapp.R;
import com.alexisboiz.covoitapp.carpool_area.carpool_detail.CarpoolAreaDetail;
import com.alexisboiz.covoitapp.manager.CacheManager;
import com.alexisboiz.covoitapp.model.API_Data.Fields;
import com.alexisboiz.covoitapp.model.API_Data.Record;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;

public class MapFragment extends Fragment {

    CarpoolAreaData carpoolAreaData;
    LatLng userLoc = new LatLng(0,0);


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @SuppressLint("NewApi")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            carpoolAreaData = CacheManager.getInstance().getCarpoolAreaData();
            if (getArguments() != null) {
                userLoc = getArguments().getParcelable(MainActivity.USER_LOCATION_KEY);
            }
            buildPinsOnMap(googleMap);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
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

    public void buildPinsOnMap(GoogleMap map) {
        List<Record> recordList = carpoolAreaData.getRecords();

        LatLng origin = userLoc;
        map.moveCamera(CameraUpdateFactory.newLatLng(origin));
        pointToPosition(origin,map);

        for (Record record : recordList) {
            List<Double> coord = record.getFields().getCoordonnees();
            LatLng pin = new LatLng(coord.get(0), coord.get(1));
            map.addMarker(new MarkerOptions().position(pin).title(record.getFields().getNomDuLieu()));
        }
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                LatLng areaCoord = marker.getPosition();
                Fields itemField = carpoolAreaData.getFieldsWithCoordinate(areaCoord);
                Intent i = new Intent(getContext(), CarpoolAreaDetail.class);
                i.putExtra(CarpoolAreaDetail.DATA, (Serializable) itemField);
                startActivity(i);
                Toast.makeText(getContext(),"Clicked on " + marker.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void pointToPosition(LatLng position, GoogleMap map) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(7.0f).build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}