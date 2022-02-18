package com.example.cocobodbusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Driver_schedule extends AppCompatActivity {

    private static final String TAG="driver_schedule";

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

    public void mondayDate(){

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

                break;

            case Calendar.TUESDAY:
                calendar.add(Calendar.DATE,6);
                Date date2=calendar.getTime();
                String dateTuesday=simpleDateFormat.format(date2);
                Log.d(TAG, "mondayDate: "+dateTuesday);

                break;

            case Calendar.WEDNESDAY:
                calendar.add(Calendar.DATE,5);
                Date date3=calendar.getTime();
                String dateWednesday=simpleDateFormat.format(date3);
                Log.d(TAG, "mondayDate: "+dateWednesday);

                break;

            case Calendar.THURSDAY:
                calendar.add(Calendar.DATE,4);
                Date date4=calendar.getTime();
                String dateThursday=simpleDateFormat.format(date4);
                Log.d(TAG, "mondayDate: "+dateThursday);

                break;

            case Calendar.FRIDAY:
                calendar.add(Calendar.DATE,3);
                Date date5=calendar.getTime();
                String dateFriday=simpleDateFormat.format(date5);
                Log.d(TAG, "mondayDate: "+dateFriday);

                break;

            case Calendar.SATURDAY:
                calendar.add(Calendar.DATE,2);
                Date date6=calendar.getTime();
                String dateSaturday=simpleDateFormat.format(date6);
                Log.d(TAG, "mondayDate: "+dateSaturday);

                break;


        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_schedule);

        mondayDate();

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


        mondayMorning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    try {
                        ParseObject schedule = new ParseObject("Schedule");
                        schedule.put("Bus", ParseUser.getCurrentUser().getUsername());
                        schedule.put("date","today");
                        schedule.put("morning",true);
                        schedule.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e==null){
                                    Log.d(TAG, "done: update successful");
                                }
                                else{
                                    Log.d(TAG, "done: update failed");
                                    Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                    }


                    Toast.makeText(Driver_schedule.this, "Monday morning set to available", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onCheckedChanged: Monday morning set to available ");
                }
                else if (!isChecked){

                    try {
                        ParseObject schedule = new ParseObject("Schedule");
                        schedule.put("Bus", ParseUser.getCurrentUser().getUsername());
                        schedule.put("date","today");
                        schedule.put("morning",false);
                        schedule.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e==null){
                                    Log.d(TAG, "done: update successful");
                                }
                                else{
                                    Log.d(TAG, "done: update failed");
                                    Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                                }
                            }
                        });



                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                    }


                    Toast.makeText(Driver_schedule.this, "Monday morning set to unavailable", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onCheckedChanged: Monday morning set to unavailable ");

                }


            }
        });


        mondayEvening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    try {
                        ParseObject schedule = new ParseObject("Schedule");
                        schedule.put("Bus", ParseUser.getCurrentUser().getUsername());
                        schedule.put("evening",true);

                        schedule.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e==null){
                                    Log.d(TAG, "done: update successful");
                                }
                                else{
                                    Log.d(TAG, "done: update failed");
                                    Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                                }
                            }
                        });



                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                    }


                    Toast.makeText(Driver_schedule.this, "Monday evening set to available", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onCheckedChanged: Monday morning set to available ");
                }
                else if (!isChecked){
                    try {
                        ParseObject schedule = new ParseObject("Schedule");
                        schedule.put("Bus", ParseUser.getCurrentUser().getUsername());
                        schedule.put("evening",false);

                        schedule.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e==null){
                                    Log.d(TAG, "done: update successful");
                                }
                                else{
                                    Log.d(TAG, "done: update failed");
                                    Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                                }
                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "onCheckedChanged: something went wrong,check internet connection");
                    }


                    Toast.makeText(Driver_schedule.this, "Monday evening set to unavailable", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onCheckedChanged: Monday morning set to unavailable ");

                }


            }
        });












    }





}