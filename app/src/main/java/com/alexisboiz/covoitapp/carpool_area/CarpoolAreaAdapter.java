package com.alexisboiz.covoitapp.carpool_area;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alexisboiz.covoitapp.R;
import com.alexisboiz.covoitapp.carpool_area.carpool_detail.CarpoolAreaDetail;
import com.alexisboiz.covoitapp.model.API_Data.Fields;
import com.alexisboiz.covoitapp.model.CarpoolAreaData;
import com.alexisboiz.covoitapp.utils.CarpoolAreaDiffCallback;

import java.io.Serializable;

public class CarpoolAreaAdapter extends RecyclerView.Adapter<CarpoolAreaAdapter.ViewHolder>{
    CarpoolAreaData carpoolAreaData;

    public CarpoolAreaAdapter(CarpoolAreaData carpoolAreaData){
        this.carpoolAreaData = carpoolAreaData;
    }
    @NonNull
    @Override
    public CarpoolAreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_carpool_item,parent,false);
        return new CarpoolAreaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarpoolAreaAdapter.ViewHolder holder, int position) {
        Fields itemField = carpoolAreaData.getRecords().get(position).getFields();

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

    }

    @Override
    public int getItemCount() {
        return carpoolAreaData.getRecords().size();
    }

    public void updateCarpoolAreaData(CarpoolAreaData dataset){
        CarpoolAreaDiffCallback diffCallback= new CarpoolAreaDiffCallback(this.carpoolAreaData, dataset);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.carpoolAreaData = dataset;
        diffResult.dispatchUpdatesTo(this);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_carpool_area_item;
        TextView tv_area_place, tv_area_city, tv_area_zip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_carpool_area_item = itemView.findViewById(R.id.iv_carpool_item);
            tv_area_place= itemView.findViewById(R.id.tv_carpool_item_place);
            tv_area_city= itemView.findViewById(R.id.tv_carpool_item_city);
            tv_area_zip= itemView.findViewById(R.id.tv_carpool_item_zip);

        }
    }
}
