package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.housemate.classes.Task;
import com.housemate.classes.TaskListAdapter;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private ImageButton createTaskBtn;
    private TextView displayUser;

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
                "User: " + MainActivity.currentUser.getFirstName() + " " + MainActivity.currentUser.getLastName() + "\n" +
                        "Household: " + MainActivity.currentHousehold.getHouseholdName()
        );

        taskList = Task.loadTasks(MainActivity.currentHousehold.getHouseID());
        taskRecyclerView = (RecyclerView) findViewById(R.id.task_recycler_view);
        taskAdapter = new TaskListAdapter(this, taskList);
        taskLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(taskLayoutManager);
        taskRecyclerView.setAdapter(taskAdapter);

        createTaskBtn = findViewById(R.id.createTaskBtn);
        createTaskBtn.setOnClickListener(button -> startActivity(
                new Intent(this, CreateTaskActivity.class)));
    }

    public void onSwitchHousehold(View view) {
        Intent intent = new Intent(this, SelectHouse.class);
        startActivity(intent);
    }
}

