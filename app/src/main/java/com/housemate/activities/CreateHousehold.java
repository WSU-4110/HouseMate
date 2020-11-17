package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.housemate.classes.Household;

public class CreateHousehold extends AppCompatActivity {
    private EditText houseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_household);
        houseName = (EditText)findViewById(R.id.createHousehold_name);
    }

    public void createHouseholdGroup(View view) {
        String name = houseName.getText().toString();
        MainActivity.currentHousehold.setHouseholdName(name);
        MainActivity.currentHousehold.createHousehold();
        if (MainActivity.currentHousehold.getHouseID() != -1) {
            MainActivity.currentUser.joinHouseholdById(MainActivity.currentHousehold.getHouseID());
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Failed to create Household Group!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

    }
}