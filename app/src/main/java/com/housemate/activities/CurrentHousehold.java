package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.housemate.adapters.CurrentHouseholdRVAdapter;
import com.housemate.classes.RemoveHousemateDialogue;

import java.util.ArrayList;



public class CurrentHousehold extends AppCompatActivity implements CurrentHouseholdRVAdapter.ItemClickListener,
        RemoveHousemateDialogue.RemoveHousemateDialogueListener, RenameHouseFragment.RenameHouseFragmentListener {
    TextView householdNameTV, householdTitleTV;
    Button saveBtn, leaveHouseBtn, getHouseKeyBtn;
    RecyclerView housemateRecView;
    CurrentHouseholdRVAdapter currentHouseHoldRVAdapter;
    ArrayList<String> housemateList,  userIdList;
    int recyclerViewPosition;
    private RenameHouseFragment renameHouseFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_current_household);

        householdNameTV = findViewById(R.id.householdNameTV);
        householdTitleTV = findViewById(R.id.householdTitleTV);
        saveBtn = findViewById(R.id.saveBtn);
        leaveHouseBtn = findViewById(R.id.leaveHouseBtn);
        getHouseKeyBtn = findViewById(R.id.getHouseKeyBtn);
        housemateRecView = findViewById(R.id.housemateRecView);

        householdNameTV.setText(MainActivity.currentHousehold.getHouseholdName());

        housemateList = MainActivity.currentHousehold.getUsers();
        userIdList = MainActivity.currentHousehold.getUserIdList();

        setUpRecyclerView();

        renameHouseFragment = new RenameHouseFragment();
        renameHouseFragment.setHouseName(MainActivity.currentHousehold.getHouseholdName());
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
        recyclerViewPosition = position;
        RemoveHousemateDialogue dialogue = new RemoveHousemateDialogue();
        dialogue.show(getSupportFragmentManager(), "removeHousemateDialogue");
    }


    // remove housemate dialogue response
    @Override
    public void applyDialogueResponse(Boolean response) {
        if (response) {
            String userIdStr = userIdList.get(recyclerViewPosition);
            int userId = Integer.parseInt(userIdStr);
            MainActivity.currentHousehold.removeHousemateFromHousehold(userId);
            housemateList.remove(recyclerViewPosition);
            userIdList.remove(recyclerViewPosition);
            currentHouseHoldRVAdapter.notifyItemRemoved(recyclerViewPosition);
        }
    }




    public void onChangeHouseholdName(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, renameHouseFragment)
                .commit();
    }


    // household name received from renameHouseFragment
    @Override
    public void onHouseNameSent(CharSequence houseName) {
        MainActivity.currentHousehold.renameHousehold(houseName.toString());
        householdNameTV.setText(houseName);
    }

    public void onLeaveHouseBtnClicked(View view) {
        MainActivity.currentUser.leaveHousehold(MainActivity.currentHousehold.getHouseID());

        MainActivity.currentHousehold.setHouseID(-1);
        MainActivity.currentHousehold.setHouseholdName("");

        Intent intent = new Intent(this, SelectHouse.class);
        startActivity(intent);
    }

    public void onGetHouseKeyBtnClicked(View view) {
        Intent intent = new Intent(this, GetHouseKey.class);
        startActivity(intent);
    }
}