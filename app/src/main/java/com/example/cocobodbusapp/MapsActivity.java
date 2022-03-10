package com.example.cocobodbusapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.cocobodbusapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.nostra13.universalimageloader.utils.L;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    public ParseGeoPoint getGeoPoint() {
        return driverGeoPoint;
    }

    public void setGeoPoint(ParseGeoPoint geoPoint) {
        this.driverGeoPoint = geoPoint;
    }

    ParseGeoPoint driverGeoPoint;
    LocationManager locationManager;
    LocationListener locationListener;
    private static final String TAG = "Maps activity";


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {


            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    try {
                        UpdateMap(lastKnownLocation);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }

    public void refreshMap(View view) {
        Log.d(TAG, "refreshMap: map refreshed");

    }

    public void UpdateMap(Location location) throws IOException {


        StringBuilder stringBuilder = new StringBuilder();
        ParseGeoPoint locParse = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

        ParseGeoPoint driverGeoPointrec = getGeoPoint();

        //      Double destinationInMiles=driverGeoPointrec.distanceInMilesTo(locParse);


        //    Double distanceOneDP=(double) Math .round(destinationInMiles-10)/10;
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng driverLocation = new LatLng(driverGeoPointrec.getLatitude(), driverGeoPointrec.getLongitude());
        Log.d(TAG, "UpdateMap: driver locates" + driverLocation.toString());

        List<LatLng> ls_pos = new ArrayList<>();
//        ls_pos.add(userLocation);
//        ls_pos.add(driverLocation);

        ArrayList<Marker> markers = new ArrayList<>();
        markers.add(mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location")));
        markers.add(mMap.addMarker(new MarkerOptions().position(driverLocation).title("Bus")));


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());

        }

        LatLngBounds bounds = builder.build();
        int padding = 30;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=AIzaSyBZv7qP-rVklHlbRPYMqbL_sxopKd6s4rE")
                .method("GET", null)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: request failedx ");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                assert response.body() != null;
                String jsonData=response.body().string();
                Log.d(TAG, "UpdateMap: tis received " + jsonData);
                List<LatLng> res_pos = PolyUtil.decode(jsonData);

                for (LatLng recieved : res_pos) {

                    ls_pos.add(recieved);
                }

            }
        });




//        try {
//            JSONObject jObject=new JSONObject(jsonData);
//            JSONArray jsonArray=jObject.getJSONArray("polyline");
//
//            for (JSONObject rec2:jObject
//                 ) {
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }




        // ls_pos.addAll(res_pos);




        mMap.animateCamera(cu);
        mMap.addPolyline(new PolylineOptions().addAll(ls_pos).width(10).color(Color.RED).visible(true));
        ls_pos.clear();

//        mMap.clear();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 20));
//        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));

    }

    public void locateBus() {


        Intent reqTitle = getIntent();
        String vehicleTitle = reqTitle.getStringExtra("vehicleTitle");
        Log.d(TAG, "onMapReady:vehicle found " + vehicleTitle);


        ParseQuery<ParseUser> busCoordinates = ParseUser.getQuery();
        busCoordinates.whereEqualTo("username", vehicleTitle);

        busCoordinates.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    for (ParseUser object : objects) {

                        ParseGeoPoint geoPoint = object.getParseGeoPoint("trackPoint");

                        setGeoPoint(geoPoint);


                        Log.d(TAG, "done: coordinates are" + geoPoint.toString());

                        Log.d(TAG, "locateBus: the bus is at" + geoPoint.toString());


                    }

                }

                Log.d(TAG, "done: driver is at" + driverGeoPoint.toString());
            }
        });


//        LatLng busLocation = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
//
//        mMap.clear();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(busLocation, 50));
//        mMap.addMarker(new MarkerOptions().position(busLocation).title("Bus Location"));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locateBus();

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        ProgressBar mapProgressBar = findViewById(R.id.mapProgressBar);
        mapProgressBar.setVisibility(View.VISIBLE);

        mMap = googleMap;


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        //Add a marker in Sydney and move the camera

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                // UpdateMap(location);
//                LatLng usersLocation = new LatLng(location.getLatitude(), location.getLongitude());
//                mMap.clear();
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usersLocation, 40));
//                mMap.addMarker(new MarkerOptions().position(usersLocation).title("YourLocation"));


            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Toast.makeText(MapsActivity.this, "Please turn on Location service", Toast.LENGTH_SHORT).show();
            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "Update your android, this version is obsolete", Toast.LENGTH_SHORT).show();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


                if (lastKnownLocation != null) {

                    mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                        @Override
                        public void onMapLoaded() {
                            try {
                                UpdateMap(lastKnownLocation);
                            } catch (IOException e) {
                                Log.d(TAG, "onMapLoaded: didn't work"+e.toString());
                                e.printStackTrace();
                            }
                        }
                    });

                } else{
                    Log.d(TAG, "onMapReady: lastknowLocation is empty");
                }
            }

        }


    }

//    @Override
//    public void onBackPressed() {
//
//        finishActivity(1);
//
//
//    }
}