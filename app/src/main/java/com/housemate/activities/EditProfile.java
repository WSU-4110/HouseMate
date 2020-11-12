package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.housemate.classes.User;

public class EditProfile extends AppCompatActivity {
    User user;
    Button backBtn, saveBtn;
    EditText fnameET, lnameET, usernameET, emailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        user = User.getInstance();
        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);
        fnameET = findViewById(R.id.fnameET);
        lnameET = findViewById(R.id.lnameET);
        usernameET = findViewById(R.id.usernameET);
        emailET = findViewById(R.id.emailET);

        fillUserData();
    }

    public void fillUserData(){
        fnameET.setText(user.getFirstName());
        lnameET.setText(user.getLastName());
        usernameET.setText(user.getUser_name());
        emailET.setText(user.getEmail());
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
            if(!editedFirstName.equals(user.getFirstName())){
                user.updateFirstNameInDB(editedFirstName);
            }
            if(!editedLastName.equals(user.getLastName())){
                user.updateLastNameInDB(editedLastName);
            }
            if(!editedUserName.equals(user.getUser_name())){
                user.updateUsernameInDB(editedUserName);
            }
            if(!editedEmail.equals(user.getEmail())) {
                user.updateEmailInDB(editedEmail);
            }
        }
    }

    boolean isEmailValid(CharSequence email) {
        return  android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }




}