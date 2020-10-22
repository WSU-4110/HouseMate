package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.housemate.classes.Household;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SelectHouse extends AppCompatActivity {
    private LinearLayout ll;
    private LinearLayout.LayoutParams lp;
    private int numHouses;
    private View.OnClickListener btnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonClicked(v);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_house);
        ArrayList<Integer> houseList =  MainActivity.currentUser.getHouseId();
        ll = (LinearLayout) findViewById(R.id.selectHouse);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numHouses = houseList.size();
        for (int i = 0; i < numHouses; i++) {
            Button b = new Button(this);
            b.setId(houseList.get(i));
            b.setText(Household.getHouseholdInfo(houseList.get(i)));
            b.setOnClickListener(btnClicked);
            ll.addView(b,lp);
        }

    }

    private void buttonClicked(View v) {
        int id = v.getId();
        if (id != MainActivity.currentHousehold.getHouseID()) {MainActivity.currentHousehold.setHousehold(id);}
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }


}