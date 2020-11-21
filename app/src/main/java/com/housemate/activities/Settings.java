package com.housemate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.housemate.classes.DeleteAccountDialogue;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class Settings extends AppCompatActivity {

    private Button profileSettings;
    private Switch completedTaskpush;
    private Switch createdTaskpush;
    private RadioButton lightTheme;
    private RadioButton darkTheme;
    private Button leaveHouseholdbutton;
    private Button logoutButton;
    private Button changePasswordbutton;
    private Button deleteAcct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        profileSettings = (Button) findViewById(R.id.profileSettingsbutton);
        completedTaskpush = (Switch) findViewById(R.id.completedTaskpush);
        createdTaskpush = (Switch) findViewById(R.id.createdTaskspush);
        lightTheme = (RadioButton) findViewById(R.id.lightTheme);
        darkTheme = (RadioButton) findViewById(R.id.darkTheme);
        leaveHouseholdbutton = (Button) findViewById(R.id.leaveHouseholdbutton);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        deleteAcct = (Button) findViewById(R.id.deleteAcct);
        changePasswordbutton = (Button) findViewById(R.id.changePasswordbutton);


        profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, EditProfile.class);
                startActivity(intent);
            }
        });

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
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.this, .class);
//                startActivity(intent);
//            }
//        });
//
//        changePasswordbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.this, .class);
//                startActivity(intent);
//            }
//        });

        deleteAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DeleteAccountDialogue deleteAccountDialogue = new DeleteAccountDialogue();
               deleteAccountDialogue.show(getSupportFragmentManager(), "deleteAccountDialogue");
             }
        });



    }
}

