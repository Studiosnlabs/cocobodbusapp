package com.example.cocobodbusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
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

public class UserBus_search extends AppCompatActivity implements SearchView.OnQueryTextListener {

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
    ListView list;
    SearchView editsearch;
    String[] destinationList;
    ListViewAdapter adapter;

    ArrayList<Destinations> darrayList=new ArrayList<Destinations>();


    private RecyclerView recyclerView;
    private ArrayList<BusFeed> arrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bus_search);
        Log.d(TAG, "onCreate: started nicely");

        destinationList = new String[]{"Tema", "Pokuase", "Madina",
                "Ashongman", "Kasoa", "Lapaz", "Adenta", "East Legon",
                "Lakeside","Kwabenya","Sakumono"};

        list = (ListView) findViewById(R.id.listview);

        for (int i = 0; i < destinationList.length; i++) {
            Destinations destinationsNames = new Destinations(destinationList[i]);
            // Binds all strings into an array
            darrayList.add(destinationsNames);
        }

        adapter=new ListViewAdapter(this,darrayList);

        list.setAdapter(adapter);

        editsearch=findViewById(R.id.busSearch);
        editsearch.setOnQueryTextListener(this);

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

        ArrayList<String> morningStates=new ArrayList<String>();
        ArrayList<String> eveningStates=new ArrayList<String>();


        ParseQuery<ParseObject> busSchedule=ParseQuery.getQuery("Schedule");
        busSchedule.whereEqualTo("date",today);

        //Todo:change date to today


        busSchedule.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){


                    if (objects.size()>0){

                        for (ParseObject object:objects) {

                            vehicleTitle=object.getString("Bus");
                            Log.d(TAG, "done: vehicle"+vehicleTitle);

                            String morningStatusM=object.getString("morning");
                            Log.d(TAG, "done: morning "+morningStatusM);

                            String eveningStatusE=object.getString("evening");
                            Log.d(TAG, "done: evening "+eveningStatusE);





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
                                                            arrayList.add(new BusFeed(vehicleImage,vehicleTitleS,morningStatusM,eveningStatusE,driverName,location,division,trackPoint));



                                                        }
                                                        else{
                                                            Log.d(TAG, "done: image error" + e);
                                                        }




                                                    }



                                                });


                                                recyclerView=findViewById(R.id.recyclerView);
                                                RecyclerAdapter recyclerAdapter= new RecyclerAdapter(arrayList);
                                                recyclerView.setAdapter(recyclerAdapter);
                                                recyclerView.setLayoutManager(new LinearLayoutManager(UserBus_search.this));
                                                Log.d(TAG, "done: view set");




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




//
//
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
//                                        busSchedule.whereEqualTo("date",today);
//
//                                        busSchedule.findInBackground(new FindCallback<ParseObject>() {
//                                            @Override
//                                            public void done(List<ParseObject> objects, ParseException e) {
//                                                if (e==null){
//
//                                                    if (objects.size()>0){
//                                                        for (ParseObject object:objects) {
//                                                           morningStatusS=object.getString("morning");
//                                                           eveningStatusS=object.getString("evening");
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
//                                        arrayList.add(new BusFeed(vehicleImage,vehicleTitle,morningStatusS,eveningStatusS,driverName,location,division,trackPoint));
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













    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text=newText;
        adapter.filter(text);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}