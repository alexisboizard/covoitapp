package com.alexisboiz.covoitapp.carpool_area;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexisboiz.covoitapp.R;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;

import java.io.Serializable;
public class CarpoolAreaFragment extends Fragment {
    private static final String CARPOOL_DATA = "CARPOOL_DATA";
    private static final String CONTEXT = "CTX";

    CarpoolAreaData carpoolAreaData;

    CarpoolAreaAdapter adapter;

    RecyclerView rvCarpoolArea;
    Context ctx;

    public CarpoolAreaFragment() {
        // Required empty public constructor
    }

    public static CarpoolAreaFragment newInstance(Context ctx, CarpoolAreaData carpoolAreaData) {
        CarpoolAreaFragment fragment = new CarpoolAreaFragment();
        Bundle args = new Bundle();
        args.putSerializable(CARPOOL_DATA, (Serializable) carpoolAreaData);
        args.putSerializable(CONTEXT, (Serializable) ctx);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            carpoolAreaData = (CarpoolAreaData) getArguments().getSerializable(CARPOOL_DATA);
            ctx = (Context) getArguments().getSerializable(CONTEXT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carpool_area, container, false);

        adapter = new CarpoolAreaAdapter(carpoolAreaData);
        rvCarpoolArea = view.findViewById(R.id.rv_carpool_area);
        rvCarpoolArea.setAdapter(adapter);
        rvCarpoolArea.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}