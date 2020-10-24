package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.housemate.adapters.CurrentHouseholdRVAdapter;
import com.housemate.adapters.HousemateRecViewAdapter;
import com.housemate.classes.Household;

import java.util.ArrayList;

//Display information regarding current household: Name, members, etc.
public class CurrentHousehold extends AppCompatActivity {
    TextView housematesTitleTV, householdTitleTV;
    Button backBtn, saveBtn;
    RecyclerView housemateRecView;
    CurrentHouseholdRVAdapter currentHouseHoldRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_household);

        housematesTitleTV = findViewById(R.id.housematesTitleTV);
        householdTitleTV = findViewById(R.id.houseHoldTitleTV);
        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);
        housemateRecView = findViewById(R.id.housemateRecView);

        householdTitleTV.setText(MainActivity.currentHousehold.getHouseholdName());

        printHousemates();

    }
    public void printHousemates(){
        ArrayList<String> housemateList = MainActivity.currentHousehold.getUsers();

        currentHouseHoldRVAdapter = new CurrentHouseholdRVAdapter(CurrentHousehold.this);
        housemateRecView.setAdapter(currentHouseHoldRVAdapter);
        currentHouseHoldRVAdapter.setHousemateList(housemateList);
        housemateRecView.setLayoutManager(new LinearLayoutManager(CurrentHousehold.this));
    }

    public void onBackBtnClicked(View view) {
        // go back to settings page?
    }



}