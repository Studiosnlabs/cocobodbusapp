package com.example.cocobodbusapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class userBusFragment extends Fragment {

    ImageView vehicleImage;
    TextView vehicleTitle;
    TextView morningStatus;
    TextView eveningStatus;
    TextView driverName;
    TextView location;
    TextView division;
    TextView trackBtn;
    ImageView favoriteBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.userbusitem,container,false);

        vehicleImage=view.findViewById(R.id.VehicleImage);
        vehicleTitle=view.findViewById(R.id.VehicleTitle);
        morningStatus=view.findViewById(R.id.morningStatusUndefined);
        eveningStatus=view.findViewById(R.id.eveningStatusUndefined);
        driverName=view.findViewById(R.id.driverNameDisplay);
        location=view.findViewById(R.id.locationNameDisplay);
        division=view.findViewById(R.id.divisionNameDisplay);
        trackBtn=view.findViewById(R.id.trackBtn);
        favoriteBtn=view.findViewById(R.id.unFavorite);

        return view;
    }
}
