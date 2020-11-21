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
        RemoveHousemateDialogue.RemoveHousemateDialogueListener, RenameHouseFragment.RenameHouseFragmentListener {
    TextView householdNameTV, householdTitleTV;
    TextView[] houseKey;
    Button backBtn, saveBtn;
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
        householdTitleTV = findViewById(R.id.houseHoldTitleTV);
        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);
        housemateRecView = findViewById(R.id.housemateRecView);
        householdNameTV.setText(MainActivity.currentHousehold.getHouseholdName());

        housemateList = MainActivity.currentHousehold.getUsers();
        userIdList = MainActivity.currentHousehold.getUserIdList();

        setUpRecyclerView();

        houseKey = new TextView[4];
        houseKey[0] = (TextView)findViewById(R.id.houseKeyTextView1);
        houseKey[1] = (TextView)findViewById(R.id.houseKeyTextView2);
        houseKey[2] = (TextView)findViewById(R.id.houseKeyTextView3);
        houseKey[3] = (TextView)findViewById(R.id.houseKeyTextView4);

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

    public void getHouseKey(View view) {
        char[] key = MainActivity.currentHousehold.getKey();
        for (int i = 0; i < 4; i++) {
            houseKey[i].setText(String.valueOf(key[i]));
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
}