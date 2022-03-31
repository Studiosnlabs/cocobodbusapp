package com.example.cocobodbusapp;

import static android.graphics.Color.parseColor;
import static com.google.android.gms.common.util.CollectionUtils.listOf;
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

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.maps.Image;
import com.mapbox.search.Country;
import com.mapbox.search.ResponseInfo;
import com.mapbox.search.SearchEngine;
import com.mapbox.search.SearchOptions;
import com.mapbox.search.SearchRequestTask;
import com.mapbox.search.record.HistoryRecord;
import com.mapbox.search.result.SearchResult;
import com.mapbox.search.ui.view.SearchBottomSheetView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class UserMap extends AppCompatActivity implements MapboxMap.OnMapClickListener {


    private static final String TAG = "UserMaps activity";
    private static final String ORIGIN_ICON_ID = "origin-icon-id";
    private static final String DESTINATION_ICON_ID = "destination-icon-id";
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_LINE_SOURCE_ID = "route-source-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";


    private MapView mapView;
    private MapboxMap mapboxMap;
    private DirectionsRoute currentRoute;
    private MapboxDirections client;
    private SearchEngine searchEngine;
    private SearchRequestTask searchRequestTask;
    private static final float LINE_WIDTH = 6f;
    private static final String ORIGIN_COLOR = "#3E2723";
    private static final String DESTINATION_COLOR = "#FFA000";
    private List<Point> routeCoordinateList;
    private Runnable runnable;
    private Handler handler;
    private int count = 0;
    private ValueAnimator markerIconAnimator;
    private LatLng markerIconCurrentLocation;
    Button refreshMapButton;
    Button pickUpBtn;
    Image busImage;

    // User is the destination , Bus is the Origin
    Point ORIGIN_POINT;
    Point DESTINATION_POINT;


    LatLng originP;
    LatLng destinationP;
    ParseGeoPoint driverGeoPoint;
    LocationManager locationManager;
    SearchOptions.Builder placesVars;
    LocationListener locationListener;
    GeoJsonSource dotGeoJsonSource;


    public LatLng getOriginP() {
        return originP;
    }

    public void setOriginP(LatLng originP) {
        this.originP = originP;
    }

    public LatLng getDestinationP() {
        return destinationP;
    }

    public void setDestinationP(LatLng destinationP) {
        this.destinationP = destinationP;
    }

    public Point getORIGIN_POINT() {
        return ORIGIN_POINT;
    }

    public void setORIGIN_POINT(Point ORIGIN_POINT) {
        this.ORIGIN_POINT = fromLngLat(ORIGIN_POINT.longitude(), ORIGIN_POINT.latitude());
    }

    public Point getDESTINATION_POINT() {
        return DESTINATION_POINT;
    }

    public void setDESTINATION_POINT(Point DESTINATION_POINT) {
        this.DESTINATION_POINT = fromLngLat(DESTINATION_POINT.longitude(), DESTINATION_POINT.latitude());
    }

    public ParseGeoPoint getGeoPoint() {
        return driverGeoPoint;
    }

    public void setGeoPoint(ParseGeoPoint geoPoint) {
        this.driverGeoPoint = geoPoint;
    }

    public Point convertToPoint(LatLng coordinates) {

        return fromLngLat(coordinates.getLongitude(), coordinates.getLatitude());

    }

    public void checkTime() {
        Calendar calendar = Calendar.getInstance();
        Calendar alarm = Calendar.getInstance();

        Date now = new Date();
        calendar.setTime(now);
        alarm.setTime(now);


        alarm.set(Calendar.HOUR_OF_DAY, 10);
        alarm.set(Calendar.MINUTE, 30);
        alarm.set(Calendar.SECOND, 0);

        Date deadLine = alarm.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        int difference = deadLine.compareTo(now);

        if (difference >= 0) {
            //redirect to not available screen

        } else {
            // toast locating driver.
        }


    }

    private static final TypeEvaluator<LatLng> latLngEvaluator = new TypeEvaluator<LatLng>() {

        private final LatLng latLng = new LatLng();

        @Override
        public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
            latLng.setLatitude(startValue.getLatitude()
                    + ((endValue.getLatitude() - startValue.getLatitude()) * fraction));
            latLng.setLongitude(startValue.getLongitude()
                    + ((endValue.getLongitude() - startValue.getLongitude()) * fraction));
            return latLng;
        }
    };

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


                        LatLng busLocation = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                        setOriginP(busLocation);
                        originP = busLocation;

                        setORIGIN_POINT(convertToPoint(busLocation));
                        ORIGIN_POINT = convertToPoint(busLocation);

                        Log.d(TAG, "done: coordinates are" + geoPoint.toString());

                        Log.d(TAG, "locateBus: the bus is at" + geoPoint.toString());


                    }

                }

                Log.d(TAG, "done: driver is at" + originP.toString());
            }
        });


    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;

            }
        }
        return bestLocation;
    }

    public void locateUser() {


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        //Add a marker in Sydney and move the camera

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);


                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                Log.d(TAG, "onLocationChanged: "+ userLocation.toString());
                setDestinationP(userLocation);
                destinationP = userLocation;

                setDESTINATION_POINT(convertToPoint(userLocation));
                DESTINATION_POINT = convertToPoint(userLocation);


            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                Log.d(TAG, "onProviderEnabled: location service turned on");

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Toast.makeText(UserMap.this, "Please turn on Location service", Toast.LENGTH_SHORT).show();
            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "Update your android, this version is obsolete", Toast.LENGTH_SHORT).show();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = getLastKnownLocation();


                if (lastKnownLocation != null) {

                    LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());

                    Log.d(TAG, "locateUser: " + userLocation.toString());
                    setDestinationP(userLocation);
                    destinationP = userLocation;

                    setDESTINATION_POINT(convertToPoint(userLocation));
                    DESTINATION_POINT = convertToPoint(userLocation);


                } else {
                    Log.d(TAG, "onMapReady: lastKnownLocation is empty");


//                    LatLng userLocation = new LatLng(5.551801137351561, -0.21106435037782964);
//
//                    Log.d(TAG, "locateUser: " + userLocation.toString());
//                    setDestinationP(userLocation);
//                    destinationP = userLocation;
//
//                    setDESTINATION_POINT(convertToPoint(userLocation));
//                    DESTINATION_POINT = convertToPoint(userLocation);

                    // Log.d(TAG, "locateUser: "+lastKnownLocation);
                }
            }

        }


    }


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
                                    routeCoordinateList = lineString.coordinates();
                                    Log.d(TAG, "onStyleLoaded: routeCoords" + routeCoordinateList);
//                                    LineString lineString2 = (LineString) getOriginAndDestinationFeatureCollection().features().get(0).geometry();
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



    public void reLoadMap() {


        Log.d(TAG, "reLoadMap: origin is" + getOriginP());
        Log.d(TAG, "reLoadMap: origin is" + getOriginP());


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
                                .withImage(ORIGIN_ICON_ID, Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_baseline_directions_bus_24))))
                                .withImage(DESTINATION_ICON_ID, BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.orange_marker))),


                        new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {


                                initSources(style);
                                initLayers(style);
                                getRoute(mapboxMap, ORIGIN_POINT, DESTINATION_POINT);


//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        initRunnable();
//                                    }
//                                }, 5000);


                            }
                        }


                );


            }
        });


    }

    public void relocateDriver() {

//        new java.util.Timer().schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//
//                        UserMap.runOnUiThread(new Runnable() {
//                            public void run() {
//                                Toast.makeText(activity, "Hello, world!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        Log.d(TAG, "run: updating driver loc");
//                        locateBus();
//                        reLoadMap();
//                    }
//                },
//                120000
//        );

        handler.postDelayed(new Runnable() {
            public void run() {

                Log.d(TAG, "run: updating driver loc");
                        locateBus();
                        reLoadMap();

                        handler.postDelayed(this,120000);

            }
        }, 120000);



    }


    public void runMap() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reLoadMap();

            }
        }, 5000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_usermap);

       // locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        pickUpBtn = findViewById(R.id.setPickupBtn);
        refreshMapButton=findViewById(R.id.refreshMap);
        SearchBottomSheetView searchBottomSheetView = findViewById(R.id.search_view);

        pickUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchBottomSheetView.setVisibility(View.VISIBLE);
            }
        });


        searchBottomSheetView.initializeSearch(savedInstanceState, new SearchBottomSheetView.Configuration());


        searchBottomSheetView.setSearchOptions(new SearchOptions().toBuilder()
                .proximity(ORIGIN_POINT)
                .countries(Country.GHANA)
                .limit(4)
                .build());

        searchBottomSheetView.setHideableByDrag(true);

        searchBottomSheetView.addOnSearchResultClickListener(new SearchBottomSheetView.OnSearchResultClickListener() {
            @Override
            public void onSearchResultClick(@NonNull SearchResult searchResult, @NonNull ResponseInfo responseInfo) {
                DESTINATION_POINT = searchResult.getCoordinate();
                searchBottomSheetView.setVisibility(View.GONE);
                runMap();

                Log.d(TAG, "onSearchResultClick: " + ORIGIN_POINT.toString());
            }
        });

        searchBottomSheetView.addOnHistoryClickListener(new SearchBottomSheetView.OnHistoryClickListener() {
            @Override
            public void onHistoryClick(@NonNull HistoryRecord historyRecord) {
                DESTINATION_POINT = historyRecord.getCoordinate();
                searchBottomSheetView.setVisibility(View.GONE);
                runMap();

                Log.d(TAG, "onSearchResultClick: " + DESTINATION_POINT.toString());

            }
        });


//        checkPermission();


//        Log.d(TAG, "onCreate: userloc:"+destinationP.toString());
//        Log.d(TAG, "onCreate: busloc:"+originP.toString());
//        Log.d(TAG, "onCreate: userpoint:"+getDESTINATION_POINT().toString());
//        Log.d(TAG, "onCreate: buspoint:"+getORIGIN_POINT().toString());
        locateUser();
        locateBus();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        runMap();

            }
        }, 5000);


//        new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                       relocateDriver();
//                                    }
//                                }, 120000);




        refreshMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               locateUser();
               locateBus();
               runMap();
            }
        });




    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        return false;
    }


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


}