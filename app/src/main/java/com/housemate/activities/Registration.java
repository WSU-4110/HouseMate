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
        if (!pass.equals(confirm)) {
            Context context = getApplicationContext();
            CharSequence text = "Registration failed!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        MainActivity.currentUser = new User(user, pass, "email", first, last, -1, -1);
        MainActivity.currentUser.register();

        Intent intent = new Intent(this, JoinCreateHousehold.class);
        startActivity(intent);
    }

    public void onBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}