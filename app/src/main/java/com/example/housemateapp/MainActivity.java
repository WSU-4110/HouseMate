package com.example.housemateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onRegister(null);
    }

    public void onLogin (View view) {
        String username = "sampleUser";
        String password = "samplePass";
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(username, password, type);
    }

    public void onRegister (View view) {
        String username = "newUser1";
        String password = "newPass";
        String firstName = "First";
        String lastName = "Last";
        String houseId = "1";
        String type = "register";
        String email = "email";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(username, password, type, firstName, lastName, houseId, email);


    }

}
