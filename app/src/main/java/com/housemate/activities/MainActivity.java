package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.housemate.classes.User;
import com.housemate.classes.Household;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button Login;
    private Button Register;
    private TextView Info;
    public static User currentUser;
    public static Household currentHousehold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = (EditText)findViewById(R.id.Username);
        Password = (EditText)findViewById(R.id.Password);
        Login = (Button)findViewById(R.id.Login);
        Register = (Button)findViewById(R.id.Register);
        Info = (TextView)findViewById(R.id.Info);
        currentHousehold = new Household();
        currentUser = new User();
    }

    // Function to execute user authentication
    public void onLogin (View view) {
        String username = Name.getText().toString();
        String password = Password.getText().toString();
        currentUser.setUser_name(username);
        currentUser.setUser_pass(password);
        if (currentUser.login() == 1) {
            Intent intent;
            if (currentUser.getHouseId() == -1) {
                intent = new Intent(this, JoinCreateHousehold.class);
            }
            else {
                currentHousehold.setHousehold(currentUser.getHouseId());
                intent = new Intent(this, HomePageActivity.class);
            }
            startActivity(intent);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Login Failed!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
    private void validate (String userName, String userPassword) {
        if ((userName.equals("Admin")) && (userPassword.equals("1234"))) {

        }
    }
    // Execute new user insert to database
    public void onRegister (View view) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }
}