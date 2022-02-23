package com.example.cocobodbusapp;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.util.List;

public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(@NonNull Location location) {

        if(location != null){
            // if it isn't, save it to Back4App Dashboard
            ParseGeoPoint currentUserLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

            ParseUser currentUser = ParseUser.getCurrentUser();

            if (currentUser != null) {
                currentUser.put("trackPoint", currentUserLocation);
                currentUser.saveInBackground();
            } else {
                // do something like coming back to the login activity
            }
        }
        else {
            // if it is null, do something like displaying error and coming back to the menu activity
        }

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {

    }

    @Override
    public void onFlushComplete(int requestCode) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
