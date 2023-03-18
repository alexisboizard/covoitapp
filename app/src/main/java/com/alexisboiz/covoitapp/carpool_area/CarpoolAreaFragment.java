package com.alexisboiz.covoitapp.carpool_area;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexisboiz.covoitapp.MainActivity;
import com.alexisboiz.covoitapp.R;
import com.alexisboiz.covoitapp.manager.CarpoolAreaDataManagerCallback;
import com.alexisboiz.covoitapp.manager.CarpoolAreaFragmentController;
import com.alexisboiz.covoitapp.map.MapFragment;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;
import com.alexisboiz.covoitapp.utils.EndlessRecyclerViewScrollListener;

import java.io.Serializable;

public class CarpoolAreaFragment extends Fragment {
    private static final String CARPOOL_DATA = "CARPOOL_DATA";
    private static final String CONTEXT = "CTX";

    CarpoolAreaData carpoolAreaData;
    CarpoolAreaData data;

    CarpoolAreaAdapter adapter;

    RecyclerView rvCarpoolArea;
    Context ctx;
    EndlessRecyclerViewScrollListener scrollListener;
    MapFragment mapFragment;

    CarpoolAreaFragmentController carpoolAreaFragmentController;

    public CarpoolAreaFragment() {
        // Required empty public constructor
    }
    public void init(CarpoolAreaData carpoolAreaData, MapFragment mapFragment){
        this.carpoolAreaData = carpoolAreaData;
        this.mapFragment = mapFragment;
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                carpoolAreaData = getArguments().getParcelable(MainActivity.CARPOOL_AREA_DATA_KEY, CarpoolAreaData.class);
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carpool_area, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new CarpoolAreaAdapter(carpoolAreaData);
        rvCarpoolArea = view.findViewById(R.id.rv_carpool_area);
        rvCarpoolArea.setAdapter(adapter);
        rvCarpoolArea.setLayoutManager(linearLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.v("...", "Tt : " + totalItemsCount);
                loadNextDataFromAPI(page);
            }
        };
        rvCarpoolArea.addOnScrollListener(scrollListener);
        return view;

    }

    public void loadNextDataFromAPI(int page){
        int offset = page * MainActivity.ROWS;
        carpoolAreaFragmentController = new CarpoolAreaFragmentController(offset);
        carpoolAreaFragmentController.getCarpoolAreaData(new CarpoolAreaDataManagerCallback() {
            @Override
            public void getCarpoolAreaResponseSuccess(CarpoolAreaData data) {
                carpoolAreaData.append(data);
                adapter.updateCarpoolAreaData(carpoolAreaData);
            }

            @Override
            public void getCarpoolAreaResponseError(String error) {
                Log.e("getCarpoolAreaResponseError", error);
            }
        });
    }
}