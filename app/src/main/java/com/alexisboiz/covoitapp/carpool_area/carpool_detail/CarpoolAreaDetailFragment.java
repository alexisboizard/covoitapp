package com.alexisboiz.covoitapp.carpool_area.carpool_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alexisboiz.covoitapp.R;
import com.alexisboiz.covoitapp.model.API_Data.Fields;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarpoolAreaDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarpoolAreaDetailFragment extends Fragment {

   Fields itemFields;
   TextView tv_area_name,tv_area_type,tv_area_adress,tv_area_zip,tv_area_city;

    public CarpoolAreaDetailFragment() {
        // Required empty public constructor
    }
    public static CarpoolAreaDetailFragment newInstance() {
        CarpoolAreaDetailFragment fragment = new CarpoolAreaDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carpool_area_detail, container, false);

        tv_area_name = view.findViewById(R.id.tv_area_name);
        tv_area_type = view.findViewById(R.id.tv_area_type);
        tv_area_adress = view.findViewById(R.id.tv_area_adress);
        tv_area_city = view.findViewById(R.id.tv_area_city);
        tv_area_zip = view.findViewById(R.id.tv_area_zip);

        tv_area_name.setText(itemFields.getNomDuLieu());
        tv_area_type.setText(itemFields.getTypeDeParking());
        tv_area_adress.setText(itemFields.getAdresse());
        tv_area_zip.setText(itemFields.getCodePostal());
        tv_area_city.setText(itemFields.getVille());

        return view;
    }

    public void setItemFields(Fields itemFields) {
        this.itemFields = itemFields;
    }
}