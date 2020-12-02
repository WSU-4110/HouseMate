package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class GetHouseKey extends AppCompatActivity {
    TextView[] houseKey;
    AppCompatImageButton  copyKeyButton, shareButton, backButton;
    Button getKeyButton;
    String keyString ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_get_house_key);
            houseKey = new TextView[4];
            houseKey[0] = (TextView)findViewById(R.id.houseKeyTextView1);
            houseKey[1] = (TextView)findViewById(R.id.houseKeyTextView2);
            houseKey[2] = (TextView)findViewById(R.id.houseKeyTextView3);
            houseKey[3] = (TextView)findViewById(R.id.houseKeyTextView4);

            backButton = findViewById(R.id.backButton);
            getKeyButton = findViewById(R.id.getKeyButton);
            copyKeyButton = findViewById(R.id.copyKeyButton);
            shareButton = findViewById(R.id.shareButton);

            keyString = "";
        }

        public void getHouseKey(View view) {
            char[] key = MainActivity.currentHousehold.getKey();
            keyString = String.valueOf(key);
            for (int i = 0; i < 4; i++) {
                houseKey[i].setText(String.valueOf(key[i]));
            }
    }

    public void onBackButtonClicked(View view) {
        Intent intent = new Intent(this, CurrentHousehold.class);
        startActivity(intent);
    }


    public void onCopyKey(View view) {
        if(keyString.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please generate key", Toast.LENGTH_SHORT).show();
        }
        else{
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("key", keyString);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied key to clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareKey(View view) {

        if(keyString.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please generate key", Toast.LENGTH_SHORT).show();
        }
        else {
            String message = "Enter this house key in Housemate app to join my household: " + keyString;
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Household Invite");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
    }
}