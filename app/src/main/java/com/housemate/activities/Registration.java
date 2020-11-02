package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.housemate.classes.User;

public class Registration extends AppCompatActivity {
    EditText firstNameET;
    EditText lastNameET;
    EditText emailET;
    EditText usernameET;
    EditText passwordET;
    EditText confirmPasswordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration3);
        firstNameET = (EditText)findViewById(R.id.firstName);
        lastNameET = (EditText)findViewById(R.id.lastName);
        emailET = (EditText)findViewById(R.id.Email);
        usernameET = (EditText)findViewById(R.id.edUserName);
        passwordET = (EditText)findViewById(R.id.enterPass);
        confirmPasswordET = (EditText)findViewById(R.id.Confirm);
    }


    // Execute new user insert to database
    public void register (View view) {
        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String email = emailET.getText().toString();
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        String confirm = confirmPasswordET.getText().toString();
        if (!password.equals(confirm)) {
            Context context = getApplicationContext();
            CharSequence text = "Registration failed!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        MainActivity.currentUser.setUser_name(username);
        MainActivity.currentUser.setUser_pass(password);
        MainActivity.currentUser.setEmail(email);
        MainActivity.currentUser.setFirstName(firstName);
        MainActivity.currentUser.setLastName(lastName);
        MainActivity.currentUser.register();

        Intent intent = new Intent(this, JoinCreateHousehold.class);
        startActivity(intent);
    }

    public void onBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}