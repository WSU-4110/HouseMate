package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);
        fnameET = findViewById(R.id.fnameET);
        lnameET = findViewById(R.id.lnameET);
        usernameET = findViewById(R.id.usernameET);
        emailET = findViewById(R.id.emailET);

        fillUserData();
    }



    public void fillUserData(){
        fnameET.setText(MainActivity.currentUser.getFirstName());
        lnameET.setText(MainActivity.currentUser.getLastName());
        usernameET.setText(MainActivity.currentUser.getUser_name());
        emailET.setText(MainActivity.currentUser.getEmail());
    }

    public void backToSettings(View view) {
        // go to to settings page
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    public void onSave(View view) {
        // update user info, save to DB
        String editedFirstName = fnameET.getText().toString();
       // MainActivity.currentUser.setFirstName(editedFirstName);
        MainActivity.currentUser.updateFirstNameInDB(editedFirstName);

    }

}