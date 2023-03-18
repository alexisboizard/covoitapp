package com.alexisboiz.covoitapp.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.alexisboiz.covoitapp.MainActivity;
import com.alexisboiz.covoitapp.model.API_Data.Record;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;

public class CarpoolAreaDiffCallback extends DiffUtil.Callback {

    private CarpoolAreaData oldCarpoolAreaData;
    private CarpoolAreaData newCarpoolAreaData;

    public CarpoolAreaDiffCallback(CarpoolAreaData oldCarpoolAreaData, CarpoolAreaData newCarpoolAreaData){
        this.oldCarpoolAreaData = oldCarpoolAreaData;
        this.newCarpoolAreaData = newCarpoolAreaData;
    }
    @Override
    public int getOldListSize() {
        return oldCarpoolAreaData.getRecords().size() - MainActivity.ROWS;
    }

    @Override
    public int getNewListSize() {
        return newCarpoolAreaData.getRecords().size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Record oldIem = oldCarpoolAreaData.getRecords().get(oldItemPosition);
        Record newItem = newCarpoolAreaData.getRecords().get(newItemPosition);

        if(oldIem == newItem){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Record oldIem = oldCarpoolAreaData.getRecords().get(oldItemPosition);
        Record newItem = newCarpoolAreaData.getRecords().get(newItemPosition);

        String oldItemName = oldIem.getFields().getNomDuLieu();
        String newItemName = newItem.getFields().getNomDuLieu();

        if(oldItemName.equals(newItemName)){
            return true;
        }else{
            return false;
        }
    }
}
