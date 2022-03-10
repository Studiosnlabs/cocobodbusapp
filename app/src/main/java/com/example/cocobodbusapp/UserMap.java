package com.example.cocobodbusapp;

import static android.graphics.Color.parseColor;
import static com.google.android.gms.common.util.CollectionUtils.listOf;
import static com.mapbox.common.TileStoreOptions.MAPBOX_ACCESS_TOKEN;
import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.geojson.Point.fromLngLat;
import static com.mapbox.mapboxsdk.style.expressions.Expression.color;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lineProgress;
import static com.mapbox.mapboxsdk.style.expressions.Expression.linear;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.match;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineGradient;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import static com.mapbox.search.SearchOptions.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.RouteOptions;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.plugin.Plugin;
import com.mapbox.search.Country;
import com.mapbox.search.MapboxSearchSdk;
import com.mapbox.search.SearchOptions;
import com.mapbox.search.ui.view.SearchBottomSheetView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class UserMap extends AppCompatActivity implements MapboxMap.OnMapClickListener {


    ParseGeoPoint driverGeoPoint;
    LocationManager locationManager;
    LocationListener locationListener;
    SearchOptions.Builder placesVars;
    private static final String TAG = "UserMaps activity";

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


    public void checkPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "Update your android, this version is obsolete", Toast.LENGTH_SHORT).show();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {

                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (lastKnownLocation != null) {

                        Log.d(TAG, "checkPermission: Last known location found");

                    } else {
                        Log.d(TAG, "onMapReady: lastknowLocation is empty");
                    }

                } catch (NullPointerException e) {

                    Log.d(TAG, "checkPermission: location service is turned off");
                }


            }

        }
    }


    public void UpdateMap(Location location) throws IOException {


        //ParseGeoPoint locParse = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

        // ParseGeoPoint driverGeoPointrec = getGeoPoint();

        //      Double destinationInMiles=driverGeoPointrec.distanceInMilesTo(locParse);


        //    Double distanceOneDP=(double) Math .round(destinationInMiles-10)/10;
        com.google.android.gms.maps.model.LatLng userLocation = new com.google.android.gms.maps.model.LatLng(location.getLatitude(), location.getLongitude());

        Log.d(TAG, "UpdateMap: user locates" + userLocation.toString());
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

    }


    public ParseGeoPoint getGeoPoint() {
        return driverGeoPoint;
    }

    public void setGeoPoint(ParseGeoPoint geoPoint) {
        this.driverGeoPoint = geoPoint;
    }

    private static final String ORIGIN_ICON_ID = "origin-icon-id";
    private static final String DESTINATION_ICON_ID = "destination-icon-id";
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_LINE_SOURCE_ID = "route-source-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private DirectionsRoute currentRoute;
    private MapboxDirections client;


    // User is the destination , Bus is the Origin
    private static Point ORIGIN_POINT = fromLngLat(-0.43298703258564986, 5.546315789548599);
    private static Point DESTINATION_POINT = fromLngLat(-0.1699634037499674, 5.702018086656672);

    LatLng originP = new LatLng(ORIGIN_POINT.latitude(), ORIGIN_POINT.longitude());
    LatLng destinationP = new LatLng(DESTINATION_POINT.latitude(), DESTINATION_POINT.longitude());


    private static final float LINE_WIDTH = 6f;
    private static final String ORIGIN_COLOR = "#2096F3";
    private static final String DESTINATION_COLOR = "#F84D4D";

    private String getAccessToken() {

        String token = getResources().getString(R.string.mapbox_access_token);

        return token;

    }

    private void initSources(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_LINE_SOURCE_ID, new GeoJsonOptions().withLineMetrics(true)));
        loadedMapStyle.addSource(new GeoJsonSource(ICON_SOURCE_ID, getOriginAndDestinationFeatureCollection()));
    }

    private FeatureCollection getOriginAndDestinationFeatureCollection() {
        Feature originFeature = Feature.fromGeometry(ORIGIN_POINT);
        originFeature.addStringProperty("originDestination", "origin");

        Feature destinationFeature = Feature.fromGeometry(DESTINATION_POINT);
        destinationFeature.addStringProperty("originDestination", "destination");
        return FeatureCollection.fromFeatures(new Feature[]{originFeature, destinationFeature});
    }

    private void initLayers(@NonNull Style loadedMapStyle) {
// Add the LineLayer to the map. This layer will display the directions route.
        loadedMapStyle.addLayer(new LineLayer(ROUTE_LAYER_ID, ROUTE_LINE_SOURCE_ID).withProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(LINE_WIDTH),
                lineGradient(interpolate(
                        linear(), lineProgress(),

// This is where the gradient color effect is set. 0 -> 1 makes it a two-color gradient
// See LineGradientActivity for an example of a 2+ multiple color gradient line.
                        stop(0f, color(parseColor(ORIGIN_COLOR))),
                        stop(1f, color(parseColor(DESTINATION_COLOR)))
                ))));

// Add the SymbolLayer to the map to show the origin and destination pin markers
        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).
                withProperties(iconImage(match(get("originDestination"), literal("origin"),
                        stop("origin", ORIGIN_ICON_ID),
                        stop("destination", DESTINATION_ICON_ID))),
                        iconIgnorePlacement(true),
                        iconAllowOverlap(true),
                        iconOffset(new Float[]{0f, -4f})));

        Log.d(TAG, "initLayers: markers added successfully");

    }

    private void getRoute(MapboxMap mapboxMap, Point origin, Point destination) {
        client = MapboxDirections.builder()
                .accessToken(getAccessToken())
                .routeOptions(
                        RouteOptions.builder()
                                .coordinatesList(listOf(
                                        fromLngLat(origin.longitude(), origin.latitude()), // origin
                                        fromLngLat(destination.longitude(), destination.latitude()) // destination
                                ))
                                .profile(DirectionsCriteria.PROFILE_DRIVING_TRAFFIC)
                                .overview(DirectionsCriteria.OVERVIEW_FULL)
                                .build()

                )
                .build();


        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                Timber.d("Response code: %s", response.code());

                if (response.body() == null) {
                    Log.d(TAG, "onResponse: No routes found");
                    Log.d(TAG, "getRoute: client" + client.toString());
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }

// Get the Direction API response's route
                currentRoute = response.body().routes().get(0);

                if (currentRoute != null) {
                    if (mapboxMap != null) {
                        mapboxMap.getStyle(new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {

// Retrieve and update the source designated for showing the directions route
                                GeoJsonSource originDestinationPointGeoJsonSource = style.getSourceAs(ICON_SOURCE_ID);

                                if (originDestinationPointGeoJsonSource != null) {
                                    originDestinationPointGeoJsonSource.setGeoJson(getOriginAndDestinationFeatureCollection());
                                }

// Retrieve and update the source designated for showing the directions route
                                GeoJsonSource lineLayerRouteGeoJsonSource = style.getSourceAs(ROUTE_LINE_SOURCE_ID);

// Create a LineString with the directions route's geometry and
// reset the GeoJSON source for the route LineLayer source
                                if (lineLayerRouteGeoJsonSource != null) {
// Create the LineString from the list of coordinates and then make a GeoJSON
// FeatureCollection so we can add the line to our map as a layer.
                                    LineString lineString = LineString.fromPolyline(currentRoute.geometry(), PRECISION_6);
                                    lineLayerRouteGeoJsonSource.setGeoJson(Feature.fromGeometry(lineString));
                                }
                            }
                        });
                    }
                } else {

                    Timber.d("Directions route is null");
                    Toast.makeText(UserMap.this, "Route not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Toast.makeText(UserMap.this, "Route not returned", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_usermap);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        SearchBottomSheetView searchBottomSheetView = findViewById(R.id.search_view);
        searchBottomSheetView.initializeSearch(savedInstanceState, new SearchBottomSheetView.Configuration());


        searchBottomSheetView.setSearchOptions(new SearchOptions().toBuilder()
                .proximity(ORIGIN_POINT)
                .countries(Country.GHANA)
                .limit(4)
                .build());

        checkPermission();


        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(originP)
                .include(destinationP)
                .build();


        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 15));


                UserMap.this.mapboxMap = mapboxMap;
                mapboxMap.setStyle(new Style.Builder().fromUri(Style.MAPBOX_STREETS)
                                .withImage(ORIGIN_ICON_ID, Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.blue_marker))))
                                .withImage(DESTINATION_ICON_ID, BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.red_marker))),

                        new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {


                                initSources(style);
                                initLayers(style);

                                getRoute(mapboxMap, ORIGIN_POINT, DESTINATION_POINT);


                            }
                        }


                );


            }
        });


    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        return false;
    }


}