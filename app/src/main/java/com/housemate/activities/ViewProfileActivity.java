package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewProfileActivity extends AppCompatActivity {
    Button selectHouseholdBtn;
    ImageButton backBtn, editProfileBtn;
    TextView fnameTV, lnameTV, usernameTV, emailTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        backBtn = findViewById(R.id.backBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        fnameTV = findViewById(R.id.fnameTV);
        lnameTV = findViewById(R.id.lnameTV);
        usernameTV = findViewById(R.id.usernameTV);
        emailTV = findViewById(R.id.emailTV);
        selectHouseholdBtn = findViewById(R.id.selectHouseholdBtn);

        fillUserData();
    }
    public void fillUserData(){
        fnameTV.setText("First Name: " + MainActivity.currentUser.getFirstName());
        lnameTV.setText("Last Name: " + MainActivity.currentUser.getLastName());
        usernameTV.setText("Username: " + MainActivity.currentUser.getUser_name());
        emailTV.setText("Email: " + MainActivity.currentUser.getEmail());
    }

    public void backToSettings(View view) {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
    public void gotoEditProfile(View view) {
        Intent intent = new Intent(ViewProfileActivity.this, EditProfile.class);
        startActivity(intent);
    }

    public void selectHousehold(View view) {
        Intent intent = new Intent(this, SelectHouse.class);
        startActivity(intent);
    }


}