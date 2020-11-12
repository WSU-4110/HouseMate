package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.housemate.adapters.CurrentHouseholdRVAdapter;

import java.util.ArrayList;


public class CurrentHousehold extends AppCompatActivity implements CurrentHouseholdRVAdapter.ItemClickListener {
    TextView housematesTitleTV, householdTitleTV;
    Button backBtn, saveBtn;
    RecyclerView housemateRecView;
    CurrentHouseholdRVAdapter currentHouseHoldRVAdapter;
    ArrayList<String> housemateList,  userIdList;

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

        housemateList = MainActivity.currentHousehold.getUsers();
        userIdList =  MainActivity.currentHousehold.getUserIdList();


        setUpRecyclerView();

    }

    public void setUpRecyclerView(){

        currentHouseHoldRVAdapter = new CurrentHouseholdRVAdapter(CurrentHousehold.this);
        housemateRecView.setAdapter(currentHouseHoldRVAdapter);
        currentHouseHoldRVAdapter.setHousemateList(housemateList);
        currentHouseHoldRVAdapter.setUserIdList(userIdList);
        currentHouseHoldRVAdapter.setClickListener(this);
        housemateRecView.setLayoutManager(new LinearLayoutManager(CurrentHousehold.this));
    }

    public void onBackBtnClicked(View view) {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }


    // onItemClick method is called in CurrentHouseholdRVAdapter class when a remove button click event occurs
    @Override
    public void onItemClick(View view, int position) {

            String userIdStr = userIdList.get(position);
            int userId = Integer.parseInt(userIdStr);
            MainActivity.currentHousehold.removeHousemateFromHousehold(userId);
            housemateList.remove(position);
            userIdList.remove(position);
            currentHouseHoldRVAdapter.notifyItemRemoved(position);
    }
}