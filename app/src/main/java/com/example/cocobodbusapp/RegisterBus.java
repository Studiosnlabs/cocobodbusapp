package com.example.cocobodbusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.concurrent.BlockingDeque;

public class RegisterBus extends AppCompatActivity {
    private static final String TAG="RegisterBus";

    Spinner driverDivisionSelect;
    EditText vehicleRegistrationET;
    EditText driverNameET;
    EditText passwordET;
    EditText confirmPasswordET;
    EditText destinationET;


    String vehicleRegistration;
    String busDivision;
    String driverName;
    String password;
    String destination;


    public void passwordCheck(String password_1,String password_2){

       if (password_1.equals(password_2)){
           Log.d(TAG, "passwordCheck: passwords match");
           password=confirmPasswordET.getText().toString();
       }
       else {
           Toast.makeText(RegisterBus.this, "passwords don't match!", Toast.LENGTH_SHORT).show();
       }

    }

    public void nextIntent(){
        Intent intent= new Intent(this,RegisterBus_SetDestination.class);
        startActivity(intent);

    }


    public void registerBus(){
        String pass1=passwordET.getText().toString();
        String pass2=confirmPasswordET.getText().toString();

        passwordCheck(pass1,pass2);

        vehicleRegistration=vehicleRegistrationET.getText().toString().toUpperCase();
        driverName=driverNameET.getText().toString();
        destination=destinationET.getText().toString();


        ParseUser bus = new ParseUser();
        bus.setUsername(vehicleRegistration);
        bus.setPassword(password);
        bus.put("driverName",driverName);
        bus.put("division",busDivision);
        bus.put("destination",destination);

        bus.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Log.d(TAG, "done: Sign up Successful");

                } else{
                    Log.d(TAG, "done: Sign up failed "+ e.toString());
                }
            }
        });

            nextIntent();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bus);

        driverDivisionSelect=findViewById(R.id.divisionET);
        vehicleRegistrationET=findViewById(R.id.vehicleVerifiacationET);
        driverNameET=findViewById(R.id.driversnameET);
        passwordET=findViewById(R.id.passwordET);
        confirmPasswordET=findViewById(R.id.confirmpasswordET);
        destinationET=findViewById(R.id.destinationET);
        Button signupBtn=findViewById(R.id.signUpBtn);


        String[] items = new String[]{"Select a Division", "BOD", "CHED","SPD","QCC"};

//      ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //  dropdown.setAdapter(adapter);

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, items) {
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
        driverDivisionSelect.setAdapter(spinnerArrayAdapter);

        driverDivisionSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    busDivision=selectedItemText;
                    Log.d(TAG, "onItemSelected: division: "+busDivision);

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



        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBus();
            }
        });


    }
}