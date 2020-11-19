package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class JoinHousehold extends AppCompatActivity {
    TextInputEditText houseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_household);
        houseId = (TextInputEditText) findViewById(R.id.joinHouseholdId);
    }

    public void joinHouseholdGroup(View view) {
        String key = houseId.getText().toString();
        int id = MainActivity.currentUser.joinHousehold(key);
        if (id > 0) {
            MainActivity.currentHousehold.setHousehold(id);
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