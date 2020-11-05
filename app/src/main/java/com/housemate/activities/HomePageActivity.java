package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.housemate.classes.IncompleteTask;
import com.housemate.classes.Task;
import com.housemate.classes.TaskListAdapter;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private ImageButton createTaskBtn;
    private TextView displayUser;
    private Button chatBtn;

    private List<Task> taskList;
    private RecyclerView taskRecyclerView;
    private RecyclerView.Adapter taskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        displayUser = findViewById(R.id.display_user);
        displayUser.setText(
                "User: \n" + MainActivity.currentUser.getFirstName() + " " + MainActivity.currentUser.getLastName() + "\n" +
                        "Household: \n" + MainActivity.currentHousehold.getHouseholdName()
        );

        taskList = IncompleteTask.loadHouseholdTasks(MainActivity.currentHousehold.getHouseID());
        taskRecyclerView = (RecyclerView) findViewById(R.id.task_recycler_view);
        taskAdapter = new TaskListAdapter(this, taskList, false);
        taskLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(taskLayoutManager);
        taskRecyclerView.setAdapter(taskAdapter);

        createTaskBtn = findViewById(R.id.createTaskBtn);

        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, CreateTaskActivity.class));
            }
        });


        boolean finish = getIntent().getBooleanExtra("finish", false);
        if (finish) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    public void onSwitchHousehold(View view) {
        Intent intent = new Intent(this, SelectHouse.class);
        startActivity(intent);
    }

    public void onChatBtnClicked(View view) {
        Intent intent = new Intent(this, CurrentHousehold.class);
        startActivity(intent);
    }

    public void onLogBtnClicked(View view) {
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        // do nothing
    }
}

