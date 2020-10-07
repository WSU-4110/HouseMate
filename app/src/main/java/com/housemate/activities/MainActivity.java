package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.housemate.classes.Task;
import com.housemate.classes.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //onRegister(null);
        //onLogin(null);
    }

    // Function to execute user authentication
    public void onLogin (View view) {
        User user = new User("username", "password");
        user.login();

    }

    // Execute new user insert to database
    public void onRegister (View view) {
        User user = new User("username", "password", "email", "firstname", "lastname", 1, 1);
        user.register();
    }

}