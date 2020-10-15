package com.housemate.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.housemate.classes.Task;
import com.housemate.classes.TaskListAdapter;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {
    private ArrayList<Task> taskList;
    private ArrayList<TextView> taskViews;
    private RecyclerView taskRecyclerView;
    private RecyclerView.Adapter taskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.something);

        taskList = Task.loadTasks();
        taskRecyclerView = (RecyclerView) findViewById(R.id.task_recycler_view);
        taskAdapter = new TaskListAdapter(taskList);
        taskLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(taskLayoutManager);
        taskRecyclerView.setAdapter(taskAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() { super.onResume(); }
}
