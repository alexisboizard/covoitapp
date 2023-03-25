package com.alexisboiz.covoitapp.manager;

import com.alexisboiz.covoitapp.model.CarpoolAreaData;

import java.util.ArrayList;
import java.util.List;

public class CacheManager {
    CarpoolAreaData carpoolAreaData;

    List<String> favoriteList;
    private static CacheManager instance;
    private CacheManager(){
        favoriteList = new ArrayList<>();
    }

    public static CacheManager getInstance(){
        if(instance == null){
            instance = new CacheManager();
        }
        return instance;
    }

    public void setCarpoolAreaData(CarpoolAreaData carpoolAreaData){
        this.carpoolAreaData = carpoolAreaData;
    }

    public CarpoolAreaData getCarpoolAreaData(){
        return carpoolAreaData;
    }

    public void addFavorite(String recordId){
        if(recordId != null){
            favoriteList.add(recordId);
        }
    }

    public List<String> getFavoriteList(){
        return favoriteList;
    }
}
