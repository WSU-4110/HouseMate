package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    EditText taskNameET, stickyNoteET;
    Button createTaskBtn, cancelBtn;
    ImageButton pickDateImgBtn, pickTimeImgBtn;
    TextView dateTV, timeTV;
    ArrayList<String> housemates, assignedTo;
    ListView houseMateLV ;
    Spinner repeatTaskSpinner;


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
        assignedTo = new ArrayList<>();
        housemates = new ArrayList<>();
        stickyNoteET = findViewById(R.id.stickyNoteET);
        cancelBtn = findViewById(R.id.cancelBtn);

        housemates.add("jim");
        housemates.add("micheal");
        housemates.add("pam");
        housemates.add("oscar");

        HousemateItemAdapter housemateItemAdapter = new HousemateItemAdapter(this, housemates);
        houseMateLV.setAdapter(housemateItemAdapter);


        repeatTaskSpinner = findViewById(R.id.repeatTaskSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.repeatTaskArr, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatTaskSpinner.setAdapter(adapter);
        repeatTaskSpinner.setOnItemSelectedListener(this);


        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName= taskNameET.getText().toString();
                Intent intent = new Intent(CreateTaskActivity.this, HomePageActivity.class);
                intent.putExtra(TASK_NAME_TAG, taskName);
                startActivity(intent);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTaskActivity.this, HomePageActivity.class);
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
        CheckBox assignCheckBox = ( CheckBox ) findViewById( R.id.assignCheckBox );
        assignCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    houseMateLV.setVisibility(View.VISIBLE);
                }
                else{
                    houseMateLV.setVisibility(View.GONE);
                }

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
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Log.i("date" , selectedDate);
        String printDate = "Date: " + selectedDate;
        dateTV.setText(printDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // get selected time from time picker dialog
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        String selectedTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        Log.i("selected time", selectedTime);
        String printTime = "Time: " + selectedTime;
        timeTV.setText(printTime);

    }

    // assign housemates to task via checklist
    public void onCheckboxClicked(View view) {
        CheckBox checkbox_housemate = view.findViewById(R.id.checkbox_housemate);
        String name = checkbox_housemate.getText().toString();

        boolean checked = ((CheckBox) view).isChecked(); // bool rep if checkbox checked or not
        if (checked){
            assignedTo.add(name);
            String saveName = assignedTo.get(assignedTo.size()-1);
            Log.i("assignedTo", saveName);
            Log.i("who", name);
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
        }
        Log.i("array size", String.valueOf(assignedTo.size()));
        String print = "";
        for(String x: assignedTo){
            print += x;
        }
    }



    // for the item (task frequency) selected in spinner (drop down menu)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // A item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String item = parent.getItemAtPosition(position).toString();
        Log.i("selected spinner", item);
    }
    // for no item selected in spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        Log.i("selected spinner", "nothing");
    }
}



/* task contains:
    name of task ok
    due date ok
    time ok
    who is the task assigned to ok
    sticky note ok (add checkbox, bullet point feature??)
    repeat task (every day, week, month, etc. ) ok
    priority level ok
*/

