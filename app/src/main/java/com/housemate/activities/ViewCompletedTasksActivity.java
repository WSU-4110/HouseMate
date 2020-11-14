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

import com.housemate.classes.CompletedTask;
import com.housemate.classes.IncompleteTask;
import com.housemate.classes.Task;
import com.housemate.classes.TaskListAdapter;

import java.util.List;

public class ViewCompletedTasksActivity extends AppCompatActivity {
    private Button backToHomeButton;

    private List<Task> taskList;
    private RecyclerView taskRecyclerView;
    private RecyclerView.Adapter taskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_completed_tasks);

        taskList = CompletedTask.loadHouseholdTasks(MainActivity.currentHousehold.getHouseID());
        taskRecyclerView = (RecyclerView) findViewById(R.id.completed_task_recycler_view);
        taskAdapter = new TaskListAdapter(this, taskList, true);
        taskLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(taskLayoutManager);
        taskRecyclerView.setAdapter(taskAdapter);

        backToHomeButton = findViewById(R.id.back_to_home);
        backToHomeButton.setOnClickListener(button -> startActivity(
                new Intent(this, HomePageActivity.class)));
    }
}

