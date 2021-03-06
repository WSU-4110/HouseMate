package com.housemate.activities;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.housemate.classes.DeleteAccountDialogue;
import com.housemate.classes.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class Settings extends AppCompatActivity {

    private Button logoutButton, deleteAcct;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        backBtn =  (ImageButton) findViewById(R.id.back_to_home_page);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        deleteAcct = (Button) findViewById(R.id.deleteAcct);

        //completedTaskpush.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
              //  Intent intent = new Intent(Settings.this, pushNotif.class);
                //startActivity(intent);
          //  }
        //});
        //createdTaskpush.setOnClickListener(new View.OnClickListener() {
            //  @Override
           //public void onClick(View v) {
                  //  Intent intent = new Intent(Settings.this, pushNotif.class);
                  //startActivity(intent);
          //  }
        //});
        // makes the user leave the household
//        leaveHouseholdbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.this, .class);
//                startActivity(intent);
//            }
//        });
//
//
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });


        deleteAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DeleteAccountDialogue deleteAccountDialogue = new DeleteAccountDialogue();
               deleteAccountDialogue.show(getSupportFragmentManager(), "deleteAccountDialogue");
             }
        });



    }

    public void onRequestPasswordReset(View view) {
        if (User.requestPasswordReset(MainActivity.currentUser.getEmail()) == 1) {
            Context context = getApplicationContext();
            CharSequence text = "Password Reset Email Sent";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Intent intent = new Intent(Settings.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "ERROR";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void backToHome(View view) {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
}

