package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfile extends AppCompatActivity {

    Button backBtn, saveBtn;
    EditText fnameET, lnameET, usernameET, emailET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initializeViews();
    }

    public void backToSettings(View view) {
        // go to to settings page
    }

    public void onSave(View view) {
        // update user info, save to DB
    }
    public void initializeViews(){
        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);
        fnameET = findViewById(R.id.fnameET);
        lnameET = findViewById(R.id.lnameET);
        usernameET = findViewById(R.id.usernameET);
        emailET = findViewById(R.id.emailET);

    }
}