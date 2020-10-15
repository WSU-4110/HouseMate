package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.housemate.classes.User;

public class Registration extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText username;
    EditText password;
    EditText confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration3);
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        username = (EditText)findViewById(R.id.edUserName);
        password = (EditText)findViewById(R.id.enterPass);
        confirmPassword = (EditText)findViewById(R.id.Confirm);
    }


    // Execute new user insert to database
    public void register (View view) {
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String confirm = confirmPassword.getText().toString();
        if (pass != confirm) {return;}
        MainActivity.currentUser = new User(user, pass, "email", first, last, -1, -1);
        MainActivity.currentUser.register();
    }
}