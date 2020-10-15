package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.housemate.classes.Task;
import com.housemate.classes.User;
import com.housemate.classes.Household;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button Login;
    private Button Register;
    private TextView Info;
    public static User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = (EditText)findViewById(R.id.Username);
        Password = (EditText)findViewById(R.id.Password);
        Login = (Button)findViewById(R.id.Login);
        Register = (Button)findViewById(R.id.Register);
        Info = (TextView)findViewById(R.id.Info);

        Household h1 = new Household("testHouse", 0);
        h1.createHousehold();
    }

    // Function to execute user authentication
    public void onLogin (View view) {
        String username = Name.getText().toString();
        String password = Password.getText().toString();
        currentUser = new User(username, password);
        currentUser.login();

    }
    private void validate (String userName, String userPassword) {
        if ((userName == "Admin") && (userPassword == "1234")) {
            //NOTE: With string comparisons (and comparisons of any reference types in general) in Java you'll want to use .equals()
            // rather than == since == compares addresses, which might give unexpected results, and .equals() compares values
        }
    }
    // Execute new user insert to database
    public void onRegister (View view) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

}