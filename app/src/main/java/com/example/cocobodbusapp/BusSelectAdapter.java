package com.example.cocobodbusapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class BusSelectAdapter extends PagerAdapter {
    private List<BusFeed> BusFeed;
    private LayoutInflater layoutInflater;
    private Context context;

    public BusSelectAdapter(List<com.example.cocobodbusapp.BusFeed> busFeed, LayoutInflater layoutInflater, Context context) {
        BusFeed = busFeed;
        this.layoutInflater = layoutInflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return BusFeed.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=LayoutInflater.from(context);
        View  view= layoutInflater.inflate(R.layout.userbusitem,container,false);


        ImageView vehicleImage;
        TextView vehicleTitle;
        TextView morningStatus;
        TextView eveningStatus;
        TextView driverName;
        TextView location;
        TextView division;
        TextView trackBtn;
        ImageView favoriteBtn;

        vehicleImage=view.findViewById(R.id.VehicleImage);
        vehicleTitle=view.findViewById(R.id.VehicleTitle);
        morningStatus=view.findViewById(R.id.morningStatusUndefined);
        eveningStatus=view.findViewById(R.id.eveningStatusUndefined);
        driverName=view.findViewById(R.id.driverNameDisplay);
        location=view.findViewById(R.id.locationNameDisplay);
        division=view.findViewById(R.id.divisionNameDisplay);
        trackBtn=view.findViewById(R.id.trackBtn);
        favoriteBtn=view.findViewById(R.id.unFavorite);

        trackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusFeed.get(position).getVehicleTitle();
            }
        });

        container.addView(view,0);

        return view;
    }
}
