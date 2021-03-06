package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.chrono.MinguoChronology;

public class EditProfile extends AppCompatActivity {

    Button selectHouseholdBtn;
    EditText fnameET, lnameET;
    TextView usernameET, emailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fnameET = (EditText) findViewById(R.id.fnameET);
        lnameET = (EditText) findViewById(R.id.lnameET);
        usernameET = findViewById(R.id.usernameET);
        emailET = findViewById(R.id.emailET);
        selectHouseholdBtn = findViewById(R.id.selectHouseholdBtn);

        fillUserData();
    }


    public void fillUserData(){
        fnameET.setText(MainActivity.currentUser.getFirstName());
        lnameET.setText(MainActivity.currentUser.getLastName());
        usernameET.setText(MainActivity.currentUser.getUser_name());
        emailET.setText(MainActivity.currentUser.getEmail());
    }

//    public void backToSettings(View view) {
//        Intent intent = new Intent(this, HomePageActivity.class);
//        startActivity(intent);
//    }

    public void onSave(View view) {

        String editedFirstName = fnameET.getText().toString();
        String editedLastName = lnameET.getText().toString();
//        String editedUserName = usernameET.getText().toString();
//        String editedEmail = emailET.getText().toString();

        if(editedFirstName.isEmpty() || editedLastName.isEmpty()) {/* || editedUserName.isEmpty() || !isEmailValid(editedEmail)){ */
            Toast.makeText(EditProfile.this, "Please fill out all the fields"/* " & enter valid email." */, Toast.LENGTH_SHORT).show();
        }
        else{
            MainActivity.currentUser.setFirstName(editedFirstName);
            MainActivity.currentUser.setLastName(editedLastName);
//            MainActivity.currentUser.setUser_name(editedUserName);
//            MainActivity.currentUser.setEmail(editedEmail);
            MainActivity.currentUser.updateUserData();
            Intent intent = new Intent(this, ViewProfileActivity.class);
            startActivity(intent);
        }
    }

    public void backToViewProfile(View view) {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }

//    boolean isEmailValid(CharSequence email) {
//        return  android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }

//    public void selectHousehold(View view) {
//        Intent intent = new Intent(this, SelectHouse.class);
//        startActivity(intent);
//    }
}