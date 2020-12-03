package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.housemate.classes.DatePickerFragment;
import com.housemate.classes.DiscardTaskDialogue;
import com.housemate.adapters.HousemateRecViewAdapter;
import com.housemate.classes.IncompleteTask;
import com.housemate.classes.Task;
import com.housemate.classes.TimePickerFragment;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

//extends FragmentActivity
//                          implements NoticeDialogFragment.NoticeDialogListener

public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    EditText taskNameET, notesET;
    Button createTaskBtn, cancelBtn;
    ImageButton pickDateImgBtn, pickTimeImgBtn;
    TextView dateTV, timeTV;
    static ArrayList<String> housemates;
    ArrayList<String> assignedTo;
    ArrayList<Boolean> isAssigned;
    RecyclerView housemateRecView ;
    Spinner repeatTaskSpinner;
    String taskName, repeatType, priority, taskNotes; // save all var to Task Obj.
    LocalDate dueDate;
    LocalTime dueTime;
    HousemateRecViewAdapter housemateRecViewAdapter;

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
        housemateRecView = findViewById(R.id.housemateRecView);
        assignedTo = new ArrayList<>();
        isAssigned = new ArrayList<>();
        housemates = new ArrayList<>();
        notesET = findViewById(R.id.notesET);
        cancelBtn = findViewById(R.id.cancelBtn);
        dueDate = null;
        dueTime = null;
        taskName = "New Task";

        dueDate = LocalDate.of(2020, 4, 12);
        dueTime = LocalTime.of(10, 30);

        repeatType = "Never";
        priority = "None";
        taskNotes = "";

        housemates.add("jim");
        housemates.add("micheal");
        housemates.add("pam");
        housemates.add("oscar");

        assignedTo = housemates;
//
//        housemateRecViewAdapter = new HousemateRecViewAdapter(this);
//        housemateRecView.setAdapter(housemateRecViewAdapter);
//        housemateRecViewAdapter.setHousemateList(housemates);
//        housemateRecView.setLayoutManager(new LinearLayoutManager(this));


        repeatTaskSpinner = findViewById(R.id.repeatTaskSpinner);
        ArrayAdapter<CharSequence> repeatTaskAdapter = ArrayAdapter.createFromResource(
                this, R.array.repeat_task_arr, android.R.layout.simple_spinner_item);
        repeatTaskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatTaskSpinner.setAdapter(repeatTaskAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, this.getResources().getStringArray(R.array.repeat_task_arr));
        // Create an ArrayAdapter using the string array and a default spinner layout
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //      R.array.repeatTaskArr, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        repeatTaskSpinner.setAdapter(adapter);
        repeatTaskSpinner.setOnItemSelectedListener(this);

        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskName = taskNameET.getText().toString();
                taskNotes = notesET.getText().toString();
                //String printAssignTo = assignedTo.toString();

                /*
                Log.i("taskName", taskName);
                Log.i("dueDate", dueDate) ;
                Log.i("dueTime", dueTime);
                Log.i("repeatTask", repeatTask);
                Log.i("priority", priority);
                Log.i("assigned to", printAssignTo);
                Log.i("notes" , taskNotes);
                LocalDate dueDate = LocalDate.of(2020, 4, 12);
                LocalTime dueTime = LocalTime.of(10, 30);
                 */
                IncompleteTask task = new IncompleteTask(taskName, taskNotes, assignedTo, dueDate, dueTime, repeatType);
                task.create(MainActivity.currentHousehold.getHouseID());


                Intent intent = new Intent(CreateTaskActivity.this, HomePageActivity.class);
                startActivity(intent);



                    String message = " A task has been Created!";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(
                            CreateTaskActivity.this
                    )
                            .setSmallIcon(R.drawable.ic_baseline)
                            .setContentTitle("New Notification")
                            .setContentText(message)
                            .setAutoCancel(true);

                    Intent intent2 = new Intent(CreateTaskActivity.this,
                            NotificationActivity.class);

                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent2.putExtra("message", message);

                    PendingIntent pendingIntent = PendingIntent.getActivity(CreateTaskActivity.this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(0, builder.build());
                }



        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscardTaskDialogue discardTaskDialogue = new DiscardTaskDialogue();
                discardTaskDialogue.show(getSupportFragmentManager(), "discardTaskDialogue");
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

        CheckBox assignCheckBox = (CheckBox) findViewById(R.id.assignCheckBox);
        assignCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                housemateRecViewAdapter = new HousemateRecViewAdapter(CreateTaskActivity.this);
                housemateRecView.setAdapter(housemateRecViewAdapter);
                housemateRecViewAdapter.setHousemateList(housemates);
                housemateRecView.setLayoutManager(new LinearLayoutManager(CreateTaskActivity.this));
                assignedTo = housemates;
                Log.i("housemates", housemates.toString());
                Log.i("isAssigned", isAssigned.toString());
                if (isChecked) {
                    housemateRecView.setVisibility(View.VISIBLE);
                } else {
                    housemateRecView.setVisibility(View.GONE);
                    assignedTo = housemates;
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
        dueDate = LocalDate.of(year,month,dayOfMonth);
        String printDate = "Date: " + dueDate;
        dateTV.setText(printDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // get selected time from time picker dialog
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        dueTime = LocalTime.of(hourOfDay,minute);
        String printTime = "Time: " + dueTime;
        timeTV.setText(printTime);

    }


// assign housemates to task via checklist
    public void onCheckboxClicked(View view) {
        CheckBox housemateCheckBox = view.findViewById(R.id.housemateCheckBox);
        String selectedHousemate = housemateCheckBox.getText().toString();

        boolean checked = housemateCheckBox.isChecked(); // bool rep if checkbox checked or not
        if (checked){
            assignedTo.add(selectedHousemate);
            Log.i("selected", selectedHousemate);
        }
        else{
            for( int i =0; i<assignedTo.size(); i++){
                if(assignedTo.get(i).equals(selectedHousemate)){
                    assignedTo.remove(selectedHousemate);
                }
            }
            Log.i("unselected", selectedHousemate);
        }
    }

    // for the item (task frequency) selected in spinner (drop down menu)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        repeatType = parent.getItemAtPosition(position).toString().toUpperCase();
        Log.i("selected spinner", repeatType);
    }
    // for no item selected in spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("selected spinner", "nothing");
    }


    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.lowRadioBtn:
                if (checked)
                    priority = "Low";
                    break;
            case R.id.medRadioBtn:
                if (checked)
                    priority = "Medium";
                    break;
            case R.id.highRadioBtn:
                if (checked)
                    priority = "High";
                    break;
            default:
                priority = "None";
        }
    }

}


