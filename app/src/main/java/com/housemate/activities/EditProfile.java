package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        String editedFirstName = fnameET.getText().toString();
        String editedLastName = lnameET.getText().toString();
        String editedUserName = usernameET.getText().toString();
        String editedEmail = emailET.getText().toString();

        if(editedFirstName.isEmpty() || editedLastName.isEmpty() || editedUserName.isEmpty() || !isEmailValid(editedEmail)){
            Toast.makeText(EditProfile.this, "Please fill out all the fields & enter valid email.", Toast.LENGTH_SHORT).show();
        }
        else{
            // update user data in database only if data was modified in edit text view
            if(!editedFirstName.equals(MainActivity.currentUser.getFirstName())){
                MainActivity.currentUser.updateFirstNameInDB(editedFirstName);
            }
            if(!editedLastName.equals(MainActivity.currentUser.getLastName())){
                MainActivity.currentUser.updateLastNameInDB(editedLastName);
            }
            if(!editedUserName.equals(MainActivity.currentUser.getUser_name())){
                MainActivity.currentUser.updateUsernameInDB(editedUserName);
            }
            if(!editedEmail.equals(MainActivity.currentUser.getEmail())) {
                MainActivity.currentUser.updateEmailInDB(editedEmail);
            }
        }
    }

    boolean isEmailValid(CharSequence email) {
        return  android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }




}