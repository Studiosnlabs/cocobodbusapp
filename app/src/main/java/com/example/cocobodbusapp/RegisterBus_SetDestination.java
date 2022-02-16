package com.example.cocobodbusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class RegisterBus_SetDestination extends AppCompatActivity {

List<String> destinations;
List<Object> morningRoutes;
List<Object> eveningRoutes;
String selectedDestination;
String selectedMorningRoute;
String selectedEveningRoute;
Spinner destinationsOpt;
Spinner morningRouteOpt;
Spinner eveningRouteOpt;



    private static final String TAG = "RegisterBus_SetDestination";

   /* public void getRoutes(){

        Log.d(TAG, "getRoutes: starting get");
        List<String> destinations;
        destinations= new ArrayList<>();


        ParseQuery<ParseObject> destinationOptions=ParseQuery.getQuery("Routes");
        destinationOptions.whereExists("Destination");

        destinationOptions.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    if (objects.size()>0){

                        for (ParseObject object:objects) {


                            Log.d(TAG, "done: Package returned"+ object.get("Destination").toString());


                            String DestinationS=object.get("Destination").toString();
                            //destinations.add();

//                            morningRoutes.add(object.get("morningRoute").toString());
//                            eveningRoutes.add(object.get("eveningRoute").toString());

                        }


                    }
                    else {
                        Log.d(TAG, "done: Empty object returned");
                    }
                }
                else{
                    Log.d(TAG, "done: error "+ e.toString());
                }
            }
        });


    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bus_set_destination);

        Log.d(TAG, "onCreate: started successfully");
        destinationsOpt=findViewById(R.id.destinationET);
        morningRouteOpt=findViewById(R.id.morningRoutesET);
        eveningRouteOpt=findViewById(R.id.eveningRoutesET);
        Button finishBtn=findViewById(R.id.finishBtn);


        eveningRoutes=new ArrayList<>();
        morningRoutes= new ArrayList<>();

        ParseQuery<ParseObject> destinationOptions=ParseQuery.getQuery("Routes");
        destinationOptions.whereExists("Destination");

        destinationOptions.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    if (objects.size()>0){

                        destinations=new ArrayList<String>();
                        for (ParseObject object:objects) {


                            Log.d(TAG, "done: Package returned"+ object.get("Destination").toString());



                            String DestinationS=object.get("Destination").toString();
                            destinations.add(DestinationS);

//                            morningRoutes.add(object.get("morningRoute").toString());
//                            eveningRoutes.add(object.get("eveningRoute").toString());

                        }


                    }
                    else {
                        Log.d(TAG, "done: Empty object returned");
                    }
                }
                else{
                    Log.d(TAG, "done: error "+ e.toString());
                }
            }
        });





        Object[] destinationsArray= destinations.toArray();
        String[] destinationArrayS= new String[destinationsArray.length];
        System.arraycopy(destinationsArray,0,destinationArrayS,0,destinationsArray.length);




        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, destinationArrayS) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;

            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        destinationsOpt.setAdapter(spinnerArrayAdapter);

        destinationsOpt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
//                ((TextView) view).setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.placeHolder));
                if (position > 0) {
                    // Notify the selected item text

                    // ((TextView) view).setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
                    Log.d(TAG, "onItemSelected: " + selectedItemText);
                    selectedDestination=selectedItemText;
                    Log.d(TAG, "onItemSelected: division: "+selectedDestination);

                }

//                if (position == 1) {
//                    setSubsidiary("sqlsrv");
//                } else if (position == 2) {
//                    setSubsidiary("exec");
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       /* Object[] morningRoutesArray= morningRoutes.toArray();

        String[] morningArrayS= new String[morningRoutesArray.length];
        System.arraycopy(morningArrayS,0,morningArrayS,0,morningRoutesArray.length);

        final ArrayAdapter<String> morningSpinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, morningArrayS) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;

            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        morningRouteOpt.setAdapter(spinnerArrayAdapter);

        morningRouteOpt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
//                ((TextView) view).setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.placeHolder));
                if (position > 0) {
                    // Notify the selected item text

                    // ((TextView) view).setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
                    Log.d(TAG, "onItemSelected: " + selectedItemText);
                    selectedMorningRoute=selectedItemText;
                    Log.d(TAG, "onItemSelected: division: "+selectedMorningRoute);

                }

//                if (position == 1) {
//                    setSubsidiary("sqlsrv");
//                } else if (position == 2) {
//                    setSubsidiary("exec");
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Object[] eveningRoutesArray= eveningRoutes.toArray();
        String[] eveningArrayS= new String[eveningRoutesArray.length];
        System.arraycopy(eveningRoutesArray,0,eveningArrayS,0,eveningRoutesArray.length);

        final ArrayAdapter<String> eveningSpinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, eveningArrayS) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;

            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        eveningRouteOpt.setAdapter(spinnerArrayAdapter);

        eveningRouteOpt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
//                ((TextView) view).setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.placeHolder));
                if (position > 0) {
                    // Notify the selected item text

                    // ((TextView) view).setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
                    Log.d(TAG, "onItemSelected: " + selectedItemText);
                    selectedEveningRoute=selectedItemText;
                    Log.d(TAG, "onItemSelected: division: "+selectedEveningRoute);

                }

//                if (position == 1) {
//                    setSubsidiary("sqlsrv");
//                } else if (position == 2) {
//                    setSubsidiary("exec");
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject locationset= new ParseObject("User");
                locationset.put("Destination",selectedDestination);
                locationset.put("morningRoute",selectedMorningRoute);
                locationset.put("eveningRoute",selectedEveningRoute);
            }
        });


    }
}