package com.example.cocobodbusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login_Activity extends AppCompatActivity {

    EditText busVerificationNumber;
    EditText loginPassword;


    public void NextIntent(){
        Intent intent= new Intent(this,Driver_schedule.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        busVerificationNumber=findViewById(R.id.loginVehicleVerificationET);
        loginPassword=findViewById(R.id.loginPasswordET);
        Button loginBtn=findViewById(R.id.loginBtn);


        if (ParseUser.getCurrentUser() != null){
            NextIntent();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserNameIN = busVerificationNumber.getText().toString();
                String PassWordIN = loginPassword.getText().toString();
                ParseUser.logInInBackground(UserNameIN, PassWordIN, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user!=null && e==null){
                            Log.i("Login","Successful");
                            NextIntent();
                        }else {
                            Log.i("Login ","Failed " +e.toString());
                            Toast.makeText(Login_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        loginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){

                    String UserNameIN = busVerificationNumber.getText().toString();
                    String PassWordIN = loginPassword.getText().toString();
                    ParseUser.logInInBackground(UserNameIN, PassWordIN, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user!=null && e==null){
                                Log.i("Login","Successful");
                                NextIntent();
                            }else {
                                Log.i("Login ","Failed " +e.toString());
                                Toast.makeText(Login_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
                return false;
            }
        });






    }
}