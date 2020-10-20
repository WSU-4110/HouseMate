package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SelectHouse extends AppCompatActivity {
    private TextView housesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_house);
        housesView = (TextView) findViewById(R.id.houseList);
        String temp = "";
        ArrayList<Integer> houseList =  MainActivity.currentUser.getHouseId();
        int numHouses = houseList.size();
        for (int i = 0; i < numHouses; i++) {
            temp += houseList.get(i);
            temp += "\n";
        }
        housesView.setText(temp);
    }
}