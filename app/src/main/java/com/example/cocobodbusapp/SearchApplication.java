package com.example.cocobodbusapp;

import android.app.Application;

import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.search.MapboxSearchSdk;

public class SearchApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MapboxSearchSdk.initialize(
                this,
                getResources().getString(R.string.mapbox_access_token),
                LocationEngineProvider.getBestLocationEngine(this)

        );

    }
}
