package com.example.cocobodbusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Driver_schedule extends AppCompatActivity {

    private static final String TAG="driver_schedule";
    private static final int REQUEST_LOCATION = 1;
    SwitchMaterial mondayMorning;
    SwitchMaterial mondayEvening;
    SwitchMaterial tuesdayMorning;
    SwitchMaterial tuesdayEvening;
    SwitchMaterial wednesdayMorning;
    SwitchMaterial wednesdayEvening;
    SwitchMaterial thursdayMorning;
    SwitchMaterial thursdayEvening;
    SwitchMaterial fridayMorning;
    SwitchMaterial fridayEvening;
    SwitchMaterial allWeek;

    TextView mondayDate;
    TextView tuesdayDate;
    TextView wednesdayDate;
    TextView thursdayDate;
    TextView fridayDate;

    ParseGeoPoint geoPoint;
    LocationManager locationManager;
    LocationListener locationListener;

    public void mondayDate(TextView mondayDateLabel){

        Calendar calendar=Calendar.getInstance();

        Date now =new Date();
        calendar.setTime(now);


       int day=calendar.get(Calendar.DAY_OF_MONTH);
       int dayNum=calendar.get(Calendar.DAY_OF_WEEK);


       //int day=calendar.getFirstDayOfWeek();

        String Monday= Integer.toString(day);
        Log.d(TAG, "mondayDate: "+Monday);
        Log.d(TAG, "mondayNum: "+dayNum);
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd MMMM,yyyy");

        switch(dayNum){


            case Calendar.SUNDAY:
                calendar.add(Calendar.DATE,1);
                Date date1=calendar.getTime();
                String dateSunday=simpleDateFormat.format(date1);
                Log.d(TAG, "mondayDate: "+dateSunday);
                mondayDateLabel.setText(dateSunday);

                break;

            case Calendar.TUESDAY:
                calendar.add(Calendar.DATE,6);
                Date date2=calendar.getTime();
                String dateTuesday=simpleDateFormat.format(date2);
                Log.d(TAG, "mondayDate: "+dateTuesday);
                mondayDateLabel.setText(dateTuesday);

                break;

            case Calendar.WEDNESDAY:
                calendar.add(Calendar.DATE,5);
                Date date3=calendar.getTime();
                String dateWednesday=simpleDateFormat.format(date3);
                Log.d(TAG, "mondayDate: "+dateWednesday);
                mondayDateLabel.setText(dateWednesday);

                break;

            case Calendar.THURSDAY:
                calendar.add(Calendar.DATE,4);
                Date date4=calendar.getTime();
                String dateThursday=simpleDateFormat.format(date4);
                Log.d(TAG, "mondayDate: "+dateThursday);
                mondayDateLabel.setText(dateThursday);

                break;

            case Calendar.FRIDAY:
                calendar.add(Calendar.DATE,3);
                Date date5=calendar.getTime();
                String dateFriday=simpleDateFormat.format(date5);
                Log.d(TAG, "mondayDate: "+dateFriday);
                mondayDateLabel.setText(dateFriday);

                break;

            case Calendar.SATURDAY:
                calendar.add(Calendar.DATE,2);
                Date date6=calendar.getTime();
                String dateSaturday=simpleDateFormat.format(date6);
                Log.d(TAG, "mondayDate: "+dateSaturday);
                mondayDateLabel.setText(dateSaturday);

                break;

            case Calendar.MONDAY:
                Date date7=calendar.getTime();
                String today5=simpleDateFormat.format(date7);
                Log.d(TAG, "mondayDate: "+today5);
                mondayDateLabel.setText(today5);

                break;


        }

    }

    public void tuesdayDate(TextView mondayDateLabel){

        Calendar calendar=Calendar.getInstance();

        Date now =new Date();
        calendar.setTime(now);


        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int dayNum=calendar.get(Calendar.DAY_OF_WEEK);


        //int day=calendar.getFirstDayOfWeek();

        String Monday= Integer.toString(day);
        Log.d(TAG, "mondayDate: "+Monday);
        Log.d(TAG, "mondayNum: "+dayNum);
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd MMMM,yyyy");

        switch(dayNum){


            case Calendar.SUNDAY:
                calendar.add(Calendar.DATE,2);
                Date date1=calendar.getTime();
                String dateSunday=simpleDateFormat.format(date1);
                Log.d(TAG, "mondayDate: "+dateSunday);
                mondayDateLabel.setText(dateSunday);

                break;

            case Calendar.MONDAY:
                calendar.add(Calendar.DATE,1);
                Date date2=calendar.getTime();
                String dateTuesday=simpleDateFormat.format(date2);
                Log.d(TAG, "mondayDate: "+dateTuesday);
                mondayDateLabel.setText(dateTuesday);

                break;

            case Calendar.WEDNESDAY:
                calendar.add(Calendar.DATE,6);
                Date date3=calendar.getTime();
                String dateWednesday=simpleDateFormat.format(date3);
                Log.d(TAG, "mondayDate: "+dateWednesday);
                mondayDateLabel.setText(dateWednesday);

                break;

            case Calendar.THURSDAY:
                calendar.add(Calendar.DATE,5);
                Date date4=calendar.getTime();
                String dateThursday=simpleDateFormat.format(date4);
                Log.d(TAG, "mondayDate: "+dateThursday);
                mondayDateLabel.setText(dateThursday);

                break;

            case Calendar.FRIDAY:
                calendar.add(Calendar.DATE,4);
                Date date5=calendar.getTime();
                String dateFriday=simpleDateFormat.format(date5);
                Log.d(TAG, "mondayDate: "+dateFriday);
                mondayDateLabel.setText(dateFriday);

                break;

            case Calendar.SATURDAY:
                calendar.add(Calendar.DATE,3);
                Date date6=calendar.getTime();
                String dateSaturday=simpleDateFormat.format(date6);
                Log.d(TAG, "mondayDate: "+dateSaturday);
                mondayDateLabel.setText(dateSaturday);

                break;

            case Calendar.TUESDAY:
                Date date7=calendar.getTime();
                String today5=simpleDateFormat.format(date7);
                Log.d(TAG, "mondayDate: "+today5);
                mondayDateLabel.setText(today5);

                break;


        }

    }

    public void wednesdayDate(TextView mondayDateLabel){

        Calendar calendar=Calendar.getInstance();

        Date now =new Date();
        calendar.setTime(now);


        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int dayNum=calendar.get(Calendar.DAY_OF_WEEK);


        //int day=calendar.getFirstDayOfWeek();

        String Monday= Integer.toString(day);
        Log.d(TAG, "mondayDate: "+Monday);
        Log.d(TAG, "mondayNum: "+dayNum);
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd MMMM,yyyy");

        switch(dayNum){


            case Calendar.SUNDAY:
                calendar.add(Calendar.DATE,3);
                Date date1=calendar.getTime();
                String dateSunday=simpleDateFormat.format(date1);
                Log.d(TAG, "mondayDate: "+dateSunday);
                mondayDateLabel.setText(dateSunday);

                break;

            case Calendar.TUESDAY:
                calendar.add(Calendar.DATE,1);
                Date date2=calendar.getTime();
                String dateTuesday=simpleDateFormat.format(date2);
                Log.d(TAG, "mondayDate: "+dateTuesday);
                mondayDateLabel.setText(dateTuesday);

                break;

            case Calendar.MONDAY:
                calendar.add(Calendar.DATE,2);
                Date date3=calendar.getTime();
                String dateWednesday=simpleDateFormat.format(date3);
                Log.d(TAG, "mondayDate: "+dateWednesday);
                mondayDateLabel.setText(dateWednesday);

                break;

            case Calendar.THURSDAY:
                calendar.add(Calendar.DATE,6);
                Date date4=calendar.getTime();
                String dateThursday=simpleDateFormat.format(date4);
                Log.d(TAG, "mondayDate: "+dateThursday);
                mondayDateLabel.setText(dateThursday);

                break;

            case Calendar.FRIDAY:
                calendar.add(Calendar.DATE,5);
                Date date5=calendar.getTime();
                String dateFriday=simpleDateFormat.format(date5);
                Log.d(TAG, "mondayDate: "+dateFriday);
                mondayDateLabel.setText(dateFriday);

                break;

            case Calendar.SATURDAY:
                calendar.add(Calendar.DATE,4);
                Date date6=calendar.getTime();
                String dateSaturday=simpleDateFormat.format(date6);
                Log.d(TAG, "mondayDate: "+dateSaturday);
                mondayDateLabel.setText(dateSaturday);

                break;

            case Calendar.WEDNESDAY:
                Date date7=calendar.getTime();
                String today5=simpleDateFormat.format(date7);
                Log.d(TAG, "mondayDate: "+today5);
                mondayDateLabel.setText(today5);

                break;


        }

    }


    public void thursdayDate(TextView mondayDateLabel){

        Calendar calendar=Calendar.getInstance();

        Date now =new Date();
        calendar.setTime(now);


        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int dayNum=calendar.get(Calendar.DAY_OF_WEEK);


        //int day=calendar.getFirstDayOfWeek();

        String Monday= Integer.toString(day);
        Log.d(TAG, "mondayDate: "+Monday);
        Log.d(TAG, "mondayNum: "+dayNum);
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd MMMM,yyyy");

        switch(dayNum){


            case Calendar.SUNDAY:
                calendar.add(Calendar.DATE,4);
                Date date1=calendar.getTime();
                String dateSunday=simpleDateFormat.format(date1);
                Log.d(TAG, "mondayDate: "+dateSunday);
                mondayDateLabel.setText(dateSunday);

                break;

            case Calendar.TUESDAY:
                calendar.add(Calendar.DATE,2);
                Date date2=calendar.getTime();
                String dateTuesday=simpleDateFormat.format(date2);
                Log.d(TAG, "mondayDate: "+dateTuesday);
                mondayDateLabel.setText(dateTuesday);

                break;

            case Calendar.WEDNESDAY:
                calendar.add(Calendar.DATE,1);
                Date date3=calendar.getTime();
                String dateWednesday=simpleDateFormat.format(date3);
                Log.d(TAG, "mondayDate: "+dateWednesday);
                mondayDateLabel.setText(dateWednesday);

                break;

            case Calendar.MONDAY:
                calendar.add(Calendar.DATE,3);
                Date date4=calendar.getTime();
                String dateThursday=simpleDateFormat.format(date4);
                Log.d(TAG, "mondayDate: "+dateThursday);
                mondayDateLabel.setText(dateThursday);

                break;

            case Calendar.FRIDAY:
                calendar.add(Calendar.DATE,6);
                Date date5=calendar.getTime();
                String dateFriday=simpleDateFormat.format(date5);
                Log.d(TAG, "mondayDate: "+dateFriday);
                mondayDateLabel.setText(dateFriday);

                break;

            case Calendar.SATURDAY:
                calendar.add(Calendar.DATE,5);
                Date date6=calendar.getTime();
                String dateSaturday=simpleDateFormat.format(date6);
                Log.d(TAG, "mondayDate: "+dateSaturday);
                mondayDateLabel.setText(dateSaturday);

                break;

            case Calendar.THURSDAY:
                Date date7=calendar.getTime();
                String today5=simpleDateFormat.format(date7);
                Log.d(TAG, "mondayDate: "+today5);
                mondayDateLabel.setText(today5);

                break;


        }

    }

    public void fridayDate(TextView mondayDateLabel){

        Calendar calendar=Calendar.getInstance();

        Date now =new Date();
        calendar.setTime(now);


        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int dayNum=calendar.get(Calendar.DAY_OF_WEEK);


        //int day=calendar.getFirstDayOfWeek();

        String Monday= Integer.toString(day);
        Log.d(TAG, "mondayDate: "+Monday);
        Log.d(TAG, "mondayNum: "+dayNum);
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd MMMM,yyyy");

        switch(dayNum){


            case Calendar.SUNDAY:
                calendar.add(Calendar.DATE,5);
                Date date1=calendar.getTime();
                String dateSunday=simpleDateFormat.format(date1);
                Log.d(TAG, "mondayDate: "+dateSunday);
                mondayDateLabel.setText(dateSunday);

                break;

            case Calendar.TUESDAY:
                calendar.add(Calendar.DATE,3);
                Date date2=calendar.getTime();
                String dateTuesday=simpleDateFormat.format(date2);
                Log.d(TAG, "mondayDate: "+dateTuesday);
                mondayDateLabel.setText(dateTuesday);

                break;

            case Calendar.WEDNESDAY:
                calendar.add(Calendar.DATE,2);
                Date date3=calendar.getTime();
                String dateWednesday=simpleDateFormat.format(date3);
                Log.d(TAG, "mondayDate: "+dateWednesday);
                mondayDateLabel.setText(dateWednesday);

                break;

            case Calendar.THURSDAY:
                calendar.add(Calendar.DATE,1);
                Date date4=calendar.getTime();
                String dateThursday=simpleDateFormat.format(date4);
                Log.d(TAG, "mondayDate: "+dateThursday);
                mondayDateLabel.setText(dateThursday);

                break;

            case Calendar.MONDAY:
                calendar.add(Calendar.DATE,4);
                Date date5=calendar.getTime();
                String dateFriday=simpleDateFormat.format(date5);
                Log.d(TAG, "mondayDate: "+dateFriday);
                mondayDateLabel.setText(dateFriday);

                break;

            case Calendar.SATURDAY:
                calendar.add(Calendar.DATE,6);
                Date date6=calendar.getTime();
                String dateSaturday=simpleDateFormat.format(date6);
                Log.d(TAG, "mondayDate: "+dateSaturday);
                mondayDateLabel.setText(dateSaturday);

                break;

            case Calendar.FRIDAY:
                Date date7=calendar.getTime();
                String today5=simpleDateFormat.format(date7);
                Log.d(TAG, "mondayDate: "+today5);
                mondayDateLabel.setText(today5);

                break;

        }

    }



    public void eveningParseIt(boolean isChecked,TextView dayDate){

        if (isChecked){
            String mondayCheck=dayDate.getText().toString();

            ParseQuery<ParseObject> scheduleCheck= ParseQuery.getQuery("Schedule");
            scheduleCheck.whereEqualTo("Bus",ParseUser.getCurrentUser().getUsername());
            scheduleCheck.whereEqualTo("date",mondayCheck);

            scheduleCheck.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e==null){
                        Log.d(TAG, "done: object"+objects.size());

                        if (objects.size()>0){

                            for (ParseObject object:objects) {
                                String recID=object.getObjectId();
                                Log.d(TAG, "done: objectID"+recID);

                                ParseQuery<ParseObject> scheduleCheck2=ParseQuery.getQuery("Schedule");
                                scheduleCheck2.whereEqualTo("objectId",recID);

                                scheduleCheck2.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if (objects.size()>0){
                                            for (ParseObject object:objects) {

                                                object.put("evening","Available");
                                                object.saveInBackground();

                                            }


                                        }
                                    }
                                });





                            }

                        }
                        else{

                            try {
                                ParseObject schedule = new ParseObject("Schedule");
                                schedule.put("Bus", ParseUser.getCurrentUser().getUsername());
                                schedule.put("evening","Available");
                                schedule.put("date",mondayCheck);


                                schedule.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException en) {
                                        if (en==null){
                                            Log.d(TAG, "done: update successful");
                                        }
                                        else{
                                            Log.d(TAG, "done: update failed");
                                            Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                                        }
                                    }
                                });



                            } catch (Exception en) {
                                en.printStackTrace();
                                Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                            }


                            Toast.makeText(Driver_schedule.this, "Monday evening set to available", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onCheckedChanged: Monday morning set to available ");

                        }

                    }
                }
            });



        }
        else if (!isChecked){

            String mondayCheck=mondayDate.getText().toString();

            ParseQuery<ParseObject> scheduleCheck= ParseQuery.getQuery("Schedule");
            scheduleCheck.whereEqualTo("Bus",ParseUser.getCurrentUser().getUsername());
            scheduleCheck.whereEqualTo("date",mondayCheck);

            scheduleCheck.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e==null){
                        Log.d(TAG, "done: object"+objects.size());

                        if (objects.size()>0){

                            for (ParseObject object:objects) {
                                String recID=object.getObjectId();
                                Log.d(TAG, "done: objectID"+recID);

                                ParseQuery<ParseObject> scheduleCheck2=ParseQuery.getQuery("Schedule");
                                scheduleCheck2.whereEqualTo("objectId",recID);

                                scheduleCheck2.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if (objects.size()>0){
                                            for (ParseObject object:objects) {

                                                object.put("evening","Unavailable");
                                                object.saveInBackground();

                                            }


                                        }
                                    }
                                });





                            }

                        }
                        else{

                            try {
                                ParseObject schedule = new ParseObject("Schedule");
                                schedule.put("Bus", ParseUser.getCurrentUser().getUsername());
                                schedule.put("evening","Unavailable");
                                schedule.put("date",mondayCheck);


                                schedule.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException en) {
                                        if (en==null){
                                            Log.d(TAG, "done: update successful");
                                        }
                                        else{
                                            Log.d(TAG, "done: update failed");
                                            Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                                        }
                                    }
                                });



                            } catch (Exception en) {
                                en.printStackTrace();
                                Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                            }


                            Toast.makeText(Driver_schedule.this, "Monday evening set to available", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onCheckedChanged: Monday morning set to available ");

                        }

                    }
                }
            });

        }


    }


    public void morningParseIt(boolean isChecked,TextView dayDate){

        if (isChecked){
            String mondayCheck=dayDate.getText().toString();

            ParseQuery<ParseObject> scheduleCheck= ParseQuery.getQuery("Schedule");
            scheduleCheck.whereEqualTo("Bus",ParseUser.getCurrentUser().getUsername());
            scheduleCheck.whereEqualTo("date",mondayCheck);

            scheduleCheck.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e==null){
                        Log.d(TAG, "done: object"+objects.size());

                        if (objects.size()>0){

                            for (ParseObject object:objects) {
                                String recID=object.getObjectId();
                                Log.d(TAG, "done: objectID"+recID);

                                ParseQuery<ParseObject> scheduleCheck2=ParseQuery.getQuery("Schedule");
                                scheduleCheck2.whereEqualTo("objectId",recID);

                                scheduleCheck2.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if (objects.size()>0){
                                            for (ParseObject object:objects) {

                                                object.put("morning","Available");
                                                object.saveInBackground();

                                            }


                                        }
                                    }
                                });





                            }

                        }
                        else{

                            try {
                                ParseObject schedule = new ParseObject("Schedule");
                                schedule.put("Bus", ParseUser.getCurrentUser().getUsername());
                                schedule.put("morning","Available");
                                schedule.put("date",mondayCheck);


                                schedule.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException en) {
                                        if (en==null){
                                            Log.d(TAG, "done: update successful");
                                        }
                                        else{
                                            Log.d(TAG, "done: update failed");
                                            Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                                        }
                                    }
                                });



                            } catch (Exception en) {
                                en.printStackTrace();
                                Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                            }


                            Toast.makeText(Driver_schedule.this, "Monday evening set to available", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onCheckedChanged: Monday morning set to available ");

                        }

                    }
                }
            });



        }
        else if (!isChecked){

            String mondayCheck=mondayDate.getText().toString();

            ParseQuery<ParseObject> scheduleCheck= ParseQuery.getQuery("Schedule");
            scheduleCheck.whereEqualTo("Bus",ParseUser.getCurrentUser().getUsername());
            scheduleCheck.whereEqualTo("date",mondayCheck);

            scheduleCheck.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e==null){
                        Log.d(TAG, "done: object"+objects.size());

                        if (objects.size()>0){

                            for (ParseObject object:objects) {
                                String recID=object.getObjectId();
                                Log.d(TAG, "done: objectID"+recID);

                                ParseQuery<ParseObject> scheduleCheck2=ParseQuery.getQuery("Schedule");
                                scheduleCheck2.whereEqualTo("objectId",recID);

                                scheduleCheck2.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if (objects.size()>0){
                                            for (ParseObject object:objects) {

                                                object.put("morning","Unavailable");
                                                object.saveInBackground();

                                            }


                                        }
                                    }
                                });





                            }

                        }
                        else{

                            try {
                                ParseObject schedule = new ParseObject("Schedule");
                                schedule.put("Bus", ParseUser.getCurrentUser().getUsername());
                                schedule.put("morning","Unavailable");
                                schedule.put("date",mondayCheck);


                                schedule.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException en) {
                                        if (en==null){
                                            Log.d(TAG, "done: update successful");
                                        }
                                        else{
                                            Log.d(TAG, "done: update failed");
                                            Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                                        }
                                    }
                                });



                            } catch (Exception en) {
                                en.printStackTrace();
                                Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                            }


                            Toast.makeText(Driver_schedule.this, "Monday evening set to available", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onCheckedChanged: Monday morning set to available ");

                        }

                    }
                }
            });

        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_LOCATION:
                saveCurrentUserLocation();
                break;
        }
    }

    private void saveCurrentUserLocation() {
        // requesting permission to get user's location
        if(ActivityCompat.checkSelfPermission(Driver_schedule.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Driver_schedule.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Driver_schedule.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else {
            // getting last know user's location
            MyLocationListener myLocationListener=new MyLocationListener();
            LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria= new Criteria();
            String bestProvider=locationManager.getBestProvider(criteria,false);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            locationManager.requestLocationUpdates(bestProvider,5,10,myLocationListener);


            // checking if the location is null
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
    }

//
//    public void updateLocation(){
//
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//            ParseQuery<ParseUser> track=ParseUser.getQuery();
//            track.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
//            track.findInBackground(new FindCallback<ParseUser>() {
//                @Override
//                public void done(List<ParseUser> objects, ParseException e) {
//                    if (e ==null){
//                        if (objects.size()>0){
//
//                            for (ParseUser object:objects) {
//
//
//                                ParseGeoPoint busLocation= new ParseGeoPoint(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
//                                object.put("trackPoint",busLocation);
//
//                            }
//
//                        }
//                    }
//                }
//            });
//
//        }
//
//        }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_schedule);




       locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        saveCurrentUserLocation();

        TextView Logout=findViewById(R.id.logout);

        mondayMorning=findViewById(R.id.mondayMorningSwitch);
        mondayEvening=findViewById(R.id.mondayEveningSwitch);
        tuesdayMorning=findViewById(R.id.tuesdayMorningSwitch);
        tuesdayEvening=findViewById(R.id.tuesdayEveningSwitch);
        wednesdayMorning=findViewById(R.id.wednesdayMorningSwitch);
        wednesdayEvening=findViewById(R.id.wednesdayEveningSwitch);
        thursdayMorning=findViewById(R.id.thursdayMorningSwitch);
        thursdayEvening=findViewById(R.id.thursdayEveningSwitch);
        fridayMorning=findViewById(R.id.fridayMorningSwitch);
        fridayEvening=findViewById(R.id.fridayEveningSwitch);
        mondayDate=findViewById(R.id.mondayDate);
        tuesdayDate=findViewById(R.id.tuesdayDate);
        wednesdayDate=findViewById(R.id.wednesdayDate);
        thursdayDate=findViewById(R.id.thursdayDate);
        fridayDate=findViewById(R.id.fridayDate);
        TextView profileName=findViewById(R.id.profileName);
        allWeek=findViewById(R.id.allWeekSwitch);
        TextView driverName=findViewById(R.id.driverName);



        mondayDate(mondayDate);
        tuesdayDate(tuesdayDate);
        wednesdayDate(wednesdayDate);
        thursdayDate(thursdayDate);
        fridayDate(fridayDate);



        profileName.setText(ParseUser.getCurrentUser().getUsername());
        driverName.setText(ParseUser.getCurrentUser().get("driverName").toString());




        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent = new Intent(Driver_schedule.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        /*All week switch*/
        allWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    mondayMorning.setChecked(true);
                    mondayEvening.setChecked(true);
                    tuesdayMorning.setChecked(true);
                    tuesdayEvening.setChecked(true);
                    wednesdayMorning.setChecked(true);
                    wednesdayEvening.setChecked(true);
                    thursdayMorning.setChecked(true);
                    thursdayEvening.setChecked(true);
                    fridayMorning.setChecked(true);
                    fridayEvening.setChecked(true);


                }else {

                    mondayMorning.setChecked(false);
                    mondayEvening.setChecked(false);
                    tuesdayMorning.setChecked(false);
                    tuesdayEvening.setChecked(false);
                    wednesdayMorning.setChecked(false);
                    wednesdayEvening.setChecked(false);
                    thursdayMorning.setChecked(false);
                    thursdayEvening.setChecked(false);
                    fridayMorning.setChecked(false);
                    fridayEvening.setChecked(false);


                }





            }
        });



        /*monday switches*/

        mondayMorning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

          morningParseIt(isChecked,mondayDate);

                Toast.makeText(Driver_schedule.this, "Monday morning schedule updated", Toast.LENGTH_SHORT).show();


            }
        });


        mondayEvening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

             eveningParseIt(isChecked,mondayDate);

                Toast.makeText(Driver_schedule.this, "Monday evening schedule updated", Toast.LENGTH_SHORT).show();


            }
        });



        /*tuesday switches*/


        tuesdayMorning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                morningParseIt(isChecked,tuesdayDate);

                Toast.makeText(Driver_schedule.this, "Tuesday morning schedule updated", Toast.LENGTH_SHORT).show();


            }
        });


        tuesdayEvening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                eveningParseIt(isChecked,tuesdayDate);

                Toast.makeText(Driver_schedule.this, "Tuesday evening schedule updated", Toast.LENGTH_SHORT).show();


            }
        });


        /*wednesday switches*/

        wednesdayMorning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                morningParseIt(isChecked,wednesdayDate);

                Toast.makeText(Driver_schedule.this, "Wednesday morning schedule updated", Toast.LENGTH_SHORT).show();


            }
        });


        wednesdayEvening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                eveningParseIt(isChecked,wednesdayDate);

                Toast.makeText(Driver_schedule.this, "Wednesday evening schedule updated", Toast.LENGTH_SHORT).show();


            }
        });



        /*thursday switches*/

        thursdayMorning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                morningParseIt(isChecked,thursdayDate);

                Toast.makeText(Driver_schedule.this, "Thursday morning schedule updated", Toast.LENGTH_SHORT).show();


            }
        });


        thursdayEvening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                eveningParseIt(isChecked,thursdayDate);

                Toast.makeText(Driver_schedule.this, "Thursday evening schedule updated", Toast.LENGTH_SHORT).show();


            }
        });




        /*friday switches*/

        fridayMorning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                morningParseIt(isChecked,fridayDate);

                Toast.makeText(Driver_schedule.this, "Friday morning schedule updated", Toast.LENGTH_SHORT).show();


            }
        });


        fridayEvening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                eveningParseIt(isChecked,fridayDate);

                Toast.makeText(Driver_schedule.this, "Friday evening schedule updated", Toast.LENGTH_SHORT).show();


            }
        });



    }


    public void onBackPressed(){
        super.onBackPressed();
    }


}