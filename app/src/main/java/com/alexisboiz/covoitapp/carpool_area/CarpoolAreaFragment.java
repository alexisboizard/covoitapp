package com.alexisboiz.covoitapp.carpool_area;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexisboiz.covoitapp.MainActivity;
import com.alexisboiz.covoitapp.R;
import com.alexisboiz.covoitapp.manager.CacheManager;
import com.alexisboiz.covoitapp.manager.CarpoolAreaDataManagerCallback;
import com.alexisboiz.covoitapp.manager.CarpoolAreaFragmentController;
import com.alexisboiz.covoitapp.model.API_Data.Record;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;
import com.alexisboiz.covoitapp.utils.EndlessRecyclerViewScrollListener;

import java.util.List;

public class CarpoolAreaFragment extends Fragment {
    private static final String CARPOOL_DATA = "CARPOOL_DATA";

    CarpoolAreaData carpoolAreaData;
    CarpoolAreaAdapter adapter;

    RecyclerView rvCarpoolArea;
    EndlessRecyclerViewScrollListener scrollListener;
    CarpoolAreaFragmentController carpoolAreaFragmentController;
    String lastSearch ="";
    CarpoolAreaData resultFromSearch;

    public CarpoolAreaFragment() {
        // Required empty public constructor
    }

    public static CarpoolAreaFragment newInstance(CarpoolAreaData carpoolAreaData) {
        CarpoolAreaFragment fragment = new CarpoolAreaFragment();
        Bundle args = new Bundle();
        args.putParcelable(CARPOOL_DATA, carpoolAreaData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.carpoolAreaData = CacheManager.getInstance().getCarpoolAreaData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        retrieveFavoriteArea();
        View view = inflater.inflate(R.layout.fragment_carpool_area, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        List<String> favoriteRecordIdList = CacheManager.getInstance().getFavoriteList();
        ObservableArrayList<Record> favoriteArea = carpoolAreaData.findWithRecordId(favoriteRecordIdList);
        CarpoolAreaData favoriteData = new CarpoolAreaData();
        favoriteData.setRecords(favoriteArea);
        for(Record record : favoriteArea){
            carpoolAreaData.moveToFirst(record);
        }

        SearchView searchBar = view.findViewById(R.id.search_bar);
        searchBar.clearFocus();

        adapter = new CarpoolAreaAdapter(carpoolAreaData);

        View emptyListView = view.findViewById(R.id.empty_list);

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

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Record> records = adapter.carpoolAreaData.find(s);

                if(records.isEmpty()){
                    Log.e("onTextChanged()", "recordList is empty");
                    emptyListView.setVisibility(View.VISIBLE);
                    rvCarpoolArea.setVisibility(View.GONE);
                }else{
                    //adapter.recordList.clear();
                    adapter.setRecordList(records);
                    adapter.notifyDataSetChanged();
                    emptyListView.setVisibility(View.GONE);
                    rvCarpoolArea.setVisibility(View.VISIBLE);
                }
                lastSearch = s;
                return false;
            }
        });

        ObservableArrayList<Record> records =CacheManager.getInstance().getCarpoolAreaData().getRecords();

        records.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeChanged(positionStart,itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeInserted(positionStart,itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                adapter.notifyItemRangeChanged(fromPosition,itemCount);
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeRemoved(positionStart,itemCount);
            }
        });

        return view;

    }

    public void loadNextDataFromAPI(int page){
        int offset = page * MainActivity.ROWS;
        carpoolAreaFragmentController = new CarpoolAreaFragmentController(offset);
        carpoolAreaFragmentController.getCarpoolAreaData(new CarpoolAreaDataManagerCallback() {
            @Override
            public void getCarpoolAreaResponseSuccess(CarpoolAreaData data) {
                carpoolAreaData.append(data,false);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void getCarpoolAreaResponseError(String error) {
                Log.e("getCarpoolAreaResponseError", error);
            }
        });
    }
    private void retrieveFavoriteArea() {
        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        int favoriteListSize = settings.getInt("favoriteListSize",-1);
        if(favoriteListSize != -1){
            for(int i =0; i<favoriteListSize; i++){
                String favoriteArea = settings.getString("area_"+i, null);
                CacheManager.getInstance().addFavorite(favoriteArea);
            }
        }

    }
}