package com.alexisboiz.covoitapp.carpool_area.carpool_detail;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alexisboiz.covoitapp.R;
import com.alexisboiz.covoitapp.model.API_Data.Fields;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;

public class CarpoolAreaDetail extends AppCompatActivity {
    CarpoolAreaData carpoolAreaData;
    public final static String DATA = "carpoolAreaData";
    CarpoolAreaDetailFragment carpoolAreaDetailFragment;
    CarpoolAreaDetailMapsFragment carpoolAreaDetailMapsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_area_detail);
        Intent intent = getIntent();
        Fields itemField = (Fields) intent.getSerializableExtra(DATA);
        carpoolAreaDetailFragment = new CarpoolAreaDetailFragment();
        carpoolAreaDetailFragment.setItemFields(itemField);

        carpoolAreaDetailMapsFragment = new CarpoolAreaDetailMapsFragment();
        carpoolAreaDetailMapsFragment.setCarpoolAreaLocation(itemField.getCoordonnees().get(0),itemField.getCoordonnees().get(1));
        carpoolAreaDetailMapsFragment.setCarpoolAreaTitle(itemField.getNomDuLieu());


        getSupportFragmentManager().beginTransaction().replace(R.id.fl_info_detail, carpoolAreaDetailFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_maps_detail, carpoolAreaDetailMapsFragment).commit();
    }
}