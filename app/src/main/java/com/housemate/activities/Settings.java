package com.housemate.activities;

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

    private TextView headerSettings;
    private Button profileSettings;
    private TextView headerPushnotific;
    private Switch onOffpush;
    private TextView headerColor;
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


        headerSettings = (TextView) findViewById(R.id.headerSettings);
        profileSettings = (Button) findViewById(R.id.profileSettingsbutton);
        headerPushnotific = (TextView) findViewById(R.id.headerPushnotific);
        onOffpush = (Switch) findViewById(R.id.onOffpush);
        headerColor = (TextView) findViewById(R.id.headerColor);
        lightTheme = (RadioButton) findViewById(R.id.lightTheme);
        darkTheme = (RadioButton) findViewById(R.id.darkTheme);
        leaveHouseholdbutton = (Button) findViewById(R.id.leaveHouseholdbutton);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        deleteAcct = (Button) findViewById(R.id.deleteAcct);
        changePasswordbutton = (Button) findViewById(R.id.changePasswordbutton);


        deleteAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAccountDialogue deleteAccountDialogue = new DeleteAccountDialogue();
                deleteAccountDialogue.show(getSupportFragmentManager(), "deleteAccountDialogue");
            }
        });
    }



}

