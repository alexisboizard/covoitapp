package com.alexisboiz.covoitapp.carpool_area;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexisboiz.covoitapp.model.CarpoolAreaData;

public class CarpoolAreaAdapter extends RecyclerView.Adapter<CarpoolAreaAdapter.ViewHolder> {
    CarpoolAreaData carpoolAreaData;
    public CarpoolAreaAdapter(CarpoolAreaData carpoolAreaData){
        this.carpoolAreaData = carpoolAreaData;
    }
    @NonNull
    @Override
    public CarpoolAreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CarpoolAreaAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
