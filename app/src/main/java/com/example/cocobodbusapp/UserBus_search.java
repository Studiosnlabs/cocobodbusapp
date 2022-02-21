package com.example.cocobodbusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserBus_search extends AppCompatActivity {

    private static final String TAG = "UserBus Search";
    Bitmap vehicleImage;
    String vehicleTitle;
    String vehicleTitleS;
    Boolean morningStatus;
    Boolean eveningStatus;
    String morningStatusS;
    String eveningStatusS;
    String driverName;
    String location;
    String division;
    ParseGeoPoint trackPoint;
    String today;

    private RecyclerView recyclerView;
    private ArrayList<BusFeed> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bus_search);
        Log.d(TAG, "onCreate: started nicely");

        TextView driverLogin=findViewById(R.id.driverLogin);

        Calendar todayDate= Calendar.getInstance();
        Date now= new Date();
        todayDate.setTime(now);

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd MMMM,yyyy");
        Date scheduleDate=todayDate.getTime();
        today=simpleDateFormat.format(scheduleDate);

driverLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(UserBus_search.this, Login_Activity.class);
        startActivity(intent);
    }
});

        arrayList = new ArrayList<>();


        ParseQuery<ParseObject> busSchedule=ParseQuery.getQuery("Schedule");
        busSchedule.whereEqualTo("date",today);

        //Todo:change date to today


        busSchedule.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){

                    if (objects.size()>0){
                        for (ParseObject object:objects) {
                            morningStatusS=object.getString("morning");
                            eveningStatusS=object.getString("evening");



                            vehicleTitle=object.getString("Bus");

                            ParseQuery<ParseUser> busQuery=ParseUser.getQuery();
                            busQuery.whereEqualTo("username",vehicleTitle);
                            busQuery.findInBackground(new FindCallback<ParseUser>() {
                                @Override
                                public void done(List<ParseUser> objects, ParseException e) {
                                    if (e==null){


                                        if (objects.size()>0){

                                            for (ParseUser object:objects) {
                                                driverName=object.getString("driverName");
                                                division=object.getString("division");
                                                location=object.getString("destination");
                                                trackPoint= object.getParseGeoPoint("trackPoint");
                                                vehicleTitleS=object.getString("username");
                                                ParseFile file =(ParseFile) object.get("vehicleImage");

                                                file.getDataInBackground(new GetDataCallback() {
                                                    @Override
                                                    public void done(byte[] data, ParseException e) {

                                                        if (e == null && data != null){
                                                            vehicleImage= BitmapFactory.decodeByteArray(data,0,data.length);

                                                            arrayList.add(new BusFeed(vehicleImage,vehicleTitleS,morningStatusS,eveningStatusS,driverName,location,division,trackPoint));

                                                            recyclerView=findViewById(R.id.recyclerView);
                                                            RecyclerAdapter recyclerAdapter= new RecyclerAdapter(arrayList);
                                                            recyclerView.setAdapter(recyclerAdapter);
                                                            recyclerView.setLayoutManager(new LinearLayoutManager(UserBus_search.this));
                                                            Log.d(TAG, "done: view set");
                                                        }
                                                        else{
                                                            Log.d(TAG, "done: image error" + e);
                                                        }




                                                    }
                                                });




                                            }

                                        }


                                    } else{
                                        Log.d(TAG, "done: No such bus exists");
                                    }
                                }
                            });

                        }


                    }
                    else {
                        Log.d(TAG, "done: 2nd Q, no objects found");
                    }

                }
                else{
                    Log.d(TAG, "done: 2nd Q error"+e);
                }
            }
        });






//        ParseQuery<ParseUser> busQuery=ParseUser.getQuery();
//
//        busQuery.findInBackground(new FindCallback<ParseUser>() {
//            @Override
//            public void done(List<ParseUser> objects, ParseException e) {
//                if (e==null){
//                    Log.d(TAG, "done: query started");
//
//                    if (objects.size()>0){
//                        Log.d(TAG, "done: objects found");
//                        for (ParseUser object:objects) {
//                            vehicleTitle=object.getString("username");
//                            Log.d(TAG, "done: "+vehicleTitle);
//                            driverName=object.getString("driverName");
//                            division=object.getString("division");
//                            location=object.getString("destination");
//                            trackPoint= object.getParseGeoPoint("trackPoint");
//                            ParseFile file =(ParseFile) object.get("vehicleImage");
//
//                            file.getDataInBackground(new GetDataCallback() {
//                                @Override
//                                public void done(byte[] data, ParseException e) {
//
//                                    if (e==null && data!=null){
//                                        Log.d(TAG, "done: Image found");
//
//                                        vehicleImage= BitmapFactory.decodeByteArray(data,0,data.length);
//
//                                        ParseQuery<ParseObject> busSchedule=ParseQuery.getQuery("Schedule");
//                                        busSchedule.whereEqualTo("Bus",vehicleTitle);
//                                        busSchedule.whereEqualTo("date","22 February,2022");
//
//                                        busSchedule.findInBackground(new FindCallback<ParseObject>() {
//                                            @Override
//                                            public void done(List<ParseObject> objects, ParseException e) {
//                                                if (e==null){
//
//                                                    if (objects.size()>0){
//                                                        for (ParseObject object:objects) {
//                                                           morningStatus=object.getBoolean("morning");
//                                                            Log.d(TAG, "done: morning"+morningStatus.toString());
//                                                           eveningStatus=object.getBoolean("evening");
//
//                                                        }
//
//
//                                                    }
//                                                    else {
//                                                        Log.d(TAG, "done: 2nd Q, no objects found");
//                                                    }
//
//                                                }
//                                                else{
//                                                    Log.d(TAG, "done: 2nd Q error"+e);
//                                                }
//                                            }
//                                        });
//
//                                        Log.d(TAG, "done: vehicle return"+vehicleTitle);
//
//                                        arrayList.add(new BusFeed(vehicleImage,vehicleTitle,morningStatus,eveningStatus,driverName,location,division,trackPoint));
//
//                                        recyclerView=findViewById(R.id.recyclerView);
//                                        RecyclerAdapter recyclerAdapter= new RecyclerAdapter(arrayList);
//                                        recyclerView.setAdapter(recyclerAdapter);
//                                        recyclerView.setLayoutManager(new LinearLayoutManager(UserBus_search.this));
//                                        Log.d(TAG, "done: view set");
//
//                                    }
//
//                                    else{
//                                        Log.d(TAG, "done: image error" + e);
//                                    }
//
//
//
//                                }
//                            });
//
//
//                        }
//
//
//
//                    }
//                    else{
//                        Log.d(TAG, "done: no objects found");
//                    }
//
//                }
//                else {
//                    Log.d(TAG, "done: error"+e);
//                }
//
//            }
//        });
//
//











    }
}