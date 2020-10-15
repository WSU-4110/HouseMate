package com.housemate.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.housemate.adapters.TaskRecViewAdapter;
import com.housemate.adapters.CardRecViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private TaskRecViewAdapter adapter;
    private CardRecViewAdapter cardRecViewAdapter;
    private RecyclerView taskRecView;
    private List<String>  priorityLevelList;
    private List <List<String>> taskList;
    private List<String> lowPriority, medPriority, highPriority;
    private ImageButton createTaskBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        taskList = new ArrayList<>();
        lowPriority = new ArrayList<>();
        medPriority = new ArrayList<>();
        highPriority = new ArrayList<>();

        lowPriority.add("not important task 1");
        lowPriority.add("not important task 2");
        lowPriority.add("not important task 3");
        lowPriority.add("not important task 4");
        lowPriority.add("not important task 5");
        lowPriority.add("not important task 6");

        medPriority.add("kind of important task 1");
        medPriority.add("kind of important task 2");
        medPriority.add("kind of important task 3");
        medPriority.add("kind of important task 4");
        medPriority.add("kind of important task 5");
        medPriority.add("kind of important task 6");

        highPriority.add("urgent task 1");
        highPriority.add("urgent task 2");
        highPriority.add("urgent task 3");
        highPriority.add("urgent task 4");
        highPriority.add("urgent task 5");
        highPriority.add("urgent task 6");

        taskList.add(highPriority);
        taskList.add(medPriority);
        taskList.add(lowPriority);

        priorityLevelList = new ArrayList<>();
        priorityLevelList.add("High Priority");
        priorityLevelList.add("Medium Priority");
        priorityLevelList.add("Low Priority");



//        adapter = new TaskRecViewAdapter(this);
//        taskRecView = findViewById(R.id.taskRecView);
//        taskRecView.setAdapter(adapter);
//        adapter.setTaskList(taskList);
//        taskRecView.setLayoutManager(new LinearLayoutManager(this));

        cardRecViewAdapter = new CardRecViewAdapter(this);
        taskRecView = findViewById(R.id.taskRecView);
        taskRecView.setAdapter(cardRecViewAdapter);
        cardRecViewAdapter.setPriorityLevelList(priorityLevelList);
        cardRecViewAdapter.setTaskList(taskList);
        taskRecView.setLayoutManager(new LinearLayoutManager(this));

        createTaskBtn = findViewById(R.id.createTaskBtn);
        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, CreateTaskActivity.class));
            }
        });



    }
}

