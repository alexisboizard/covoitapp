package com.alexisboiz.covoitapp.carpool_area;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexisboiz.covoitapp.model.CarpoolAreaData;

<<<<<<< HEAD:app/src/main/java/com/alexisboiz/covoitapp/carpool_area/placeholder/CarpoolAreaAdapter.java
import java.util.List;

public class CarpoolAreaAdapter extends RecyclerView.Adapter<CarpoolAreaAdapter.ViewHolder> {
    List<CarpoolAreaData> carpoolAreaData;
=======
public class CarpoolAreaAdapter extends RecyclerView.Adapter<CarpoolAreaAdapter.ViewHolder> {
    CarpoolAreaData carpoolAreaData;
    public CarpoolAreaAdapter(CarpoolAreaData carpoolAreaData){
        this.carpoolAreaData = carpoolAreaData;
    }
>>>>>>> CarpoolAreaFragment:app/src/main/java/com/alexisboiz/covoitapp/carpool_area/CarpoolAreaAdapter.java
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
        return carpoolAreaData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
