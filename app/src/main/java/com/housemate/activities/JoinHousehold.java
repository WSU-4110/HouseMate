package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class JoinHousehold extends AppCompatActivity {
    EditText houseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_household);
        houseId = (EditText)findViewById(R.id.joinHouseholdId);
    }

    public void joinHouseholdGroup(View view) {
        int id = Integer.parseInt(houseId.getText().toString());
        MainActivity.currentUser.joinHousehold(id);
        if (MainActivity.currentUser.getHouseId() != -1) {
            MainActivity.currentHousehold.setHousehold(MainActivity.currentUser.getHouseId());
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Failed to join Household Group!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
    }
}