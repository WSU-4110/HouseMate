package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.housemate.classes.Household;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SelectHouseActivity extends AppCompatActivity {
    private LinearLayout ll, llRow, llColumn;
    private LinearLayout.LayoutParams lp, llRowParams, llColumnParams;
    private View.OnClickListener btnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonClicked(v);
        }
    };

    private View.OnClickListener joinBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goToJoin();
        }
    };

    private View.OnClickListener createBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goToCreate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_house2);
        ll = (LinearLayout) findViewById(R.id.selectHouse);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        lp.setMargins(10,5,10,5);
        llRowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        llRowParams.weight = 1;
        llColumnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llColumnParams.gravity = Gravity.CENTER;
        llRowParams.setMargins(15,15,15,15);
        ArrayList<Integer> houseIds = MainActivity.currentUser.getHouseId();
        for (int i = 0; i < houseIds.size(); i++) {
            if (i % 2 == 0) {
                llRow = new LinearLayout(this);
                llRow.setLayoutParams(lp);
                llRow.setOrientation(LinearLayout.HORIZONTAL);
                ll.addView(llRow);
            }
            llColumn = new LinearLayout(this);
            llColumn.setOrientation(LinearLayout.VERTICAL);
            ImageButton b = new ImageButton(this);
            b.setId(houseIds.get(i));
            b.setImageResource(R.drawable.ic_baseline_house_100);
            b.setLayoutParams(llColumnParams);
            b.setPadding(15,10,15,10);
            b.setBackgroundResource(R.color.green_sheen);
            b.setOnClickListener(btnClicked);
            TextView t = new TextView(this);
            t.setText(Household.getHouseholdInfo(houseIds.get(i)));
            t.setLayoutParams(llColumnParams);
            llColumn.addView(b);
            llColumn.addView(t);
            llColumn.setLayoutParams(llRowParams);
            llRow.addView(llColumn);
        }
        llRow = new LinearLayout(this);
        llRow.setLayoutParams(lp);
        llRow.setOrientation(LinearLayout.HORIZONTAL);
        llRow.setGravity(Gravity.CENTER);
        ll.addView(llRow);

        Button b = new Button(this);
        b.setText("\nJoin\nHousehold\n");
        b.setLayoutParams(llRowParams);
        b.setOnClickListener(joinBtnClicked);
        b.setBackgroundResource(R.color.atomic_tangerine);
        llRow.addView(b);

        b = new Button(this);
        b.setText("\nCreate\nNew Household\n");
        b.setOnClickListener(createBtnClicked);
        b.setLayoutParams(llRowParams);
        b.setBackgroundResource(R.color.atomic_tangerine);
        llRow.addView(b);
    }
    private void goToJoin() {
        Intent intent = new Intent(this, JoinHousehold.class);
        startActivity(intent);
    }


    private void goToCreate() {
        Intent intent = new Intent(this, CreateHousehold.class);
        startActivity(intent);
    }


    private void buttonClicked(View v) {
        int id = v.getId();
        if (id != MainActivity.currentHousehold.getHouseID()) {MainActivity.currentHousehold.setHousehold(id);}
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
}