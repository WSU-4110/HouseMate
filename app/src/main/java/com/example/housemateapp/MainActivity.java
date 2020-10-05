package com.example.housemateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static com.example.housemateapp.CreateTaskActivity.TASK_NAME_TAG;

public class MainActivity extends AppCompatActivity {
    Button addTaskBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTaskBtn = findViewById(R.id.addTaskBtn);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateTaskActivity.class));
            }
        });
        Intent intent = getIntent();
        String name = intent.getStringExtra(TASK_NAME_TAG);

        if(name!=null){

            Log.i(TASK_NAME_TAG, name);
        }
        else{
            Log.i(TASK_NAME_TAG, "task was NULL");
        }



    }
}

//Intel® HAXM installation failed. To install Intel® HAXM follow the instructions found at: