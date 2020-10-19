package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.housemate.classes.Task;

public class ViewTaskById extends AppCompatActivity {
    private EditText taskIdET;
    private TextView taskView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_by_id);
        taskIdET = (EditText)findViewById(R.id.taskIdET);
        taskView = (TextView) findViewById(R.id.taskView);
    }

    public void fetchTask(View view) {
        int taskId = Integer.parseInt(taskIdET.getText().toString());
        String taskInfo = "";
        Task temp = Task.getTaskById(taskId);
        String taskName = temp.getName();
        String taskDescription = temp.getDescription();
        taskInfo = taskInfo +
                "Task Name: \n" +
                taskName + "\n" +
                "Task Description: \n" +
                taskDescription;
        taskView.setText(taskInfo);
    }
}