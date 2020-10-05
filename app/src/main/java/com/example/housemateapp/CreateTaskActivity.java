package com.example.housemateapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText taskNameET;
    Button createTaskBtn;
    ImageButton pickDateImgBtn, pickTimeImgBtn;
    TextView dateTV;

     public static final String TASK_NAME_TAG = "taskName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        taskNameET = findViewById(R.id.taskNameET);
        createTaskBtn = findViewById(R.id.createTaskBtn);
        pickDateImgBtn = findViewById(R.id.pickDateImgBtn);
        dateTV = findViewById(R.id.dateTV);
        pickTimeImgBtn = findViewById(R.id.pickTimeImgBtn);

        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName= taskNameET.getText().toString();
                Intent intent = new Intent(CreateTaskActivity.this, MainActivity.class);
                intent.putExtra(TASK_NAME_TAG, taskName);
                startActivity(intent);
            }
        });
        pickDateImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });
        pickTimeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // get selected date from date picker dialog
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Log.i("date" , currentDateString);
        dateTV.setText(currentDateString);
    }
}

//Intel® HAXM installation failed. To install Intel® HAXM follow the instructions found at:

/* task contains:
     name of task ok
     due date ok
     time
     sticky note????
     who assigend task
     who will complete task
     priority level


 */