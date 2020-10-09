package com.example.housemateapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText taskNameET, stickyNoteET;
    Button createTaskBtn;
    ImageButton pickDateImgBtn, pickTimeImgBtn;
    TextView dateTV, timeTV;
    CheckBox checkbox_housemate;
    ArrayList<String> housemates, assignedTo;
    ListView houseMateLV ;
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
        timeTV = findViewById(R.id.timeTV);
        houseMateLV = findViewById(R.id.houseMateLV);
        checkbox_housemate = findViewById(R.id.checkbox_housemate);
        assignedTo = new ArrayList<>();
        housemates = new ArrayList<>();
        stickyNoteET = findViewById(R.id.stickyNoteET);


        housemates.add("jim");
        housemates.add("micheal");
        housemates.add("pam");
        housemates.add("oscar");

       // houseMateLV.setAdapter(new ArrayAdapter<String>(this, R.layout.housemate_listview_detail, housemates ));
        HousemateItemAdapter housemateItemAdapter = new HousemateItemAdapter(this, housemates);
        houseMateLV.setAdapter(housemateItemAdapter);

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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // create calendar obj, set obj with user selected hour and day
         Calendar c = Calendar.getInstance();
         c.set(Calendar.HOUR_OF_DAY, hourOfDay);
         c.set(Calendar.MINUTE, minute);
        String selectedTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        Log.i("formatted time", selectedTime);
        timeTV.setText(selectedTime);

    }
    public void onCheckboxClicked(View view) {

        CheckBox checkbox_housemate1 = view.findViewById(R.id.checkbox_housemate);
        String name = checkbox_housemate1.getText().toString();
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        if (checked){
            assignedTo.add(name);
            String saveName = assignedTo.get(assignedTo.size()-1);
            Log.i("assignedTo", saveName);
            //String housemateName = view.checkbox_housemate.getText().toString();
            Log.i("who", name);
            Log.i("array size", String.valueOf(assignedTo.size()));
        }
        else{
            //assignedTo.remove(name);
            Log.i("checked", "not checked");
            assignedTo.remove(name);
            for(String housemate: assignedTo){
                if(housemate.equals(name)){
                    assignedTo.remove(housemate);
                }
            }
            Log.i("array size", String.valueOf(assignedTo.size()));
        }
        String print = "";
        for(String x: assignedTo){
            print += x;
        }


        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.checkbox_housemate:
//                if (checked){
//                    Log.i("checked", "an item checked");
//                    CheckBox checkbox_housemate1 = view.findViewById(R.id.checkbox_housemate);
//                    String name = checkbox_housemate1.getText().toString();
//                    assignedTo.add(name);
//                    //String housemateName = view.checkbox_housemate.getText().toString();
//                    Log.i("who", name);
//                }
//                else{
//                    //assignedTo.remove(name);
//                    Log.i("checked", "not checked");
//                }
//                break;
//            default:
//                break;
//        }
    }

}

//Intel® HAXM installation failed. To install Intel® HAXM follow the instructions found at:

/* task contains:
     name of task ok
     due date ok
     time ok
     who is the task assigned to ok
     sticky note ok (add checkbox, bullet point feature??)
     priority level (how are tasks ranked??)
     repeat task (every day, week, month, etc. )

 */