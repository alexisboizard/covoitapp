package com.alexisboiz.covoitapp.carpool_area;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alexisboiz.covoitapp.R;
import com.alexisboiz.covoitapp.carpool_area.carpool_detail.CarpoolAreaDetail;
import com.alexisboiz.covoitapp.manager.CacheManager;
import com.alexisboiz.covoitapp.model.API_Data.Fields;
import com.alexisboiz.covoitapp.model.API_Data.Record;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;

import java.io.Serializable;
import java.util.List;

public class CarpoolAreaAdapter extends RecyclerView.Adapter<CarpoolAreaAdapter.ViewHolder> {
    CarpoolAreaData carpoolAreaData;
    List<Record> recordList;


    CacheManager cacheManager;

    Drawable fav_heart, no_fav_heart;

    public CarpoolAreaAdapter(CarpoolAreaData carpoolAreaData) {
        this.carpoolAreaData = carpoolAreaData;
        recordList = carpoolAreaData.getRecords();
        cacheManager = CacheManager.getInstance();

    }

    @NonNull
    @Override
    public CarpoolAreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_carpool_item, parent, false);
        fav_heart = ContextCompat.getDrawable(view.getContext(), R.drawable.fav_heart);
        no_fav_heart = ContextCompat.getDrawable(view.getContext(), R.drawable.no_fav_heart);
        return new CarpoolAreaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarpoolAreaAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Fields itemField;
        if(position<0){
            itemField = recordList.get(0).getFields();
        }
        else{
            itemField = recordList.get(position).getFields();
        }


        List<String> favoriteAreaList = CacheManager.getInstance().getFavoriteList();

        for(String recordId : favoriteAreaList){
            if(recordList.get(position).getRecordid().equals(recordId)){
                holder.radio_favorite.setBackground(fav_heart);
            }
        }

        holder.iv_carpool_area_item.setImageResource(R.drawable.ic_car_foreground);
        holder.tv_area_place.setText(itemField.getNomDuLieu());
        holder.tv_area_city.setText(itemField.getVille());
        holder.tv_area_zip.setText(itemField.getCodePostal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CarpoolAreaDetail.class);
                i.putExtra(CarpoolAreaDetail.DATA, (Serializable) itemField);
                holder.itemView.getContext().startActivity(i);

            }
        });
        holder.radio_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.radio_favorite.isChecked()){
                    holder.radio_favorite.setBackground(fav_heart);
                    cacheManager.addFavorite(recordList.get(position).getRecordid());
                    carpoolAreaData.moveToFirst(recordList.get(position));
                }else{
                    holder.radio_favorite.setBackground(no_fav_heart);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_carpool_area_item;

        RadioButton radio_favorite;
        TextView tv_area_place, tv_area_city, tv_area_zip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_carpool_area_item = itemView.findViewById(R.id.iv_carpool_item);
            tv_area_place = itemView.findViewById(R.id.tv_carpool_item_place);
            tv_area_city = itemView.findViewById(R.id.tv_carpool_item_city);
            tv_area_zip = itemView.findViewById(R.id.tv_carpool_item_zip);
            radio_favorite = itemView.findViewById(R.id.radio_favorite);
        }
    }
}
