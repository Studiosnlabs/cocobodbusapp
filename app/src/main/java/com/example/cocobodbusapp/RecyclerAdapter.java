package com.example.cocobodbusapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String TAG = "Bus feed list Adapter";

    ArrayList<BusFeed> arrayList;



    public RecyclerAdapter(ArrayList<BusFeed> arrayList) {
        this.arrayList = arrayList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.userbusitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BusFeed busFeed = arrayList.get(position);



        holder.vehicleImage.setImageBitmap(busFeed.getVehicleImage());
        holder.vehicleTitle.setText(busFeed.getVehicleTitle());
        holder.morningStatus.setText(busFeed.getMorningStatus());
        holder.eveningStatus.setText(busFeed.getEveningStatus());
        holder.driverName.setText(busFeed.getDriverName());
        holder.location.setText(busFeed.getLocation());
        holder.division.setText(busFeed.getDivision());
        holder.trackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.progressBar2.setVisibility(View.VISIBLE);
                try {

                    String Title= busFeed.getVehicleTitle();
                    Intent intent= new Intent(v.getContext(),UserMap.class);
                    intent.putExtra("vehicleTitle",Title);
                    v.getContext().startActivity(intent);

                   // holder.progressBar2.setVisibility(View.GONE);
                }catch (NullPointerException e){
                    Log.d(TAG, "onClick: could not fetch title");
                }
          


             




            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView vehicleImage;
        TextView vehicleTitle;
        TextView morningStatus;
        TextView eveningStatus;
        TextView driverName;
        TextView location;
        TextView division;
        TextView trackBtn;
        ImageView favoriteBtn;
        ProgressBar progressBar2;
        ParseGeoPoint geoPoint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            Context context=itemView.getContext();
            vehicleImage = itemView.findViewById(R.id.VehicleImage);
            vehicleTitle = itemView.findViewById(R.id.VehicleTitle);
            morningStatus = itemView.findViewById(R.id.morningStatusUndefined);
            eveningStatus = itemView.findViewById(R.id.eveningStatusUndefined);
            driverName = itemView.findViewById(R.id.driverNameDisplay);
            location = itemView.findViewById(R.id.locationNameDisplay);
            division = itemView.findViewById(R.id.divisionNameDisplay);
            trackBtn = itemView.findViewById(R.id.trackBtn);
            favoriteBtn = itemView.findViewById(R.id.unFavorite);
            progressBar2=itemView.findViewById(R.id.itemProgressbar);



        }
    }


}
