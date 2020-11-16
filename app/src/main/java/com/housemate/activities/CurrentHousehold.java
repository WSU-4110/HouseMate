package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.housemate.adapters.CurrentHouseholdRVAdapter;
import com.housemate.adapters.HousemateRecViewAdapter;
import com.housemate.classes.Household;
import com.housemate.classes.RemoveHousemateDialogue;

import java.util.ArrayList;


public class CurrentHousehold extends AppCompatActivity implements CurrentHouseholdRVAdapter.ItemClickListener,
        RemoveHousemateDialogue.RemoveHousemateDialogueListener {
    TextView householdNameTV, householdTitleTV;
    Button backBtn, saveBtn;
    RecyclerView housemateRecView;
    CurrentHouseholdRVAdapter currentHouseHoldRVAdapter;
    ArrayList<String> housemateList,  userIdList;
    int recyclerViewPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_household);

        householdNameTV = findViewById(R.id.householdNameTV);
        householdTitleTV = findViewById(R.id.houseHoldTitleTV);
        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);
        housemateRecView = findViewById(R.id.housemateRecView);
        householdNameTV.setText(MainActivity.currentHousehold.getHouseholdName());
        housemateList = MainActivity.currentHousehold.getUsers();
        userIdList = MainActivity.currentHousehold.getUserIdList();

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
        Intent intent = new Intent(CurrentHousehold.this, HomePageActivity.class);
        startActivity(intent);
    }


    // onItemClick method is called in CurrentHouseholdRVAdapter class when a remove button click event occurs
    @Override
    public void onItemClick(View view, int position) {
        recyclerViewPosition = position;
        RemoveHousemateDialogue dialogue = new RemoveHousemateDialogue();
        dialogue.show(getSupportFragmentManager(), "removeHousemateDialogue");
    }


    // remove housemate dialogue response
    @Override
    public void applyDialogueResponse(Boolean response) {
            if(response){
                String userIdStr = userIdList.get(recyclerViewPosition);
                int userId = Integer.parseInt(userIdStr);
                MainActivity.currentHousehold.removeHousemateFromHousehold(userId);
                housemateList.remove(recyclerViewPosition);
                userIdList.remove(recyclerViewPosition);
                currentHouseHoldRVAdapter.notifyItemRemoved(recyclerViewPosition);
            }
    }
}