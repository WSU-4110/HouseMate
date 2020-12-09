package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import android.widget.Toast;

import com.housemate.classes.DatePickerFragment;
import com.housemate.classes.DiscardTaskDialogue;
import com.housemate.adapters.HousemateRecViewAdapter;
import com.housemate.classes.IncompleteTask;
import com.housemate.classes.TimePickerFragment;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import androidx.core.app.NotificationCompat;
import android.app.NotificationManager;

//extends FragmentActivity
//                          implements NoticeDialogFragment.NoticeDialogListener

public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener,
        HousemateRecViewAdapter.CheckBoxListener{
    EditText taskNameET, notesET;
    Button createTaskBtn;
    private AppCompatImageButton pickDateImgBtn, pickTimeImgBtn, cancelBtn;
    TextView dateTV, timeTV;
    static ArrayList<String> housemates;
    ArrayList<String> assignedTo;
    Boolean[] isAssigned;
    RecyclerView housemateRecView ;
    Spinner repeatTaskSpinner;
    String taskName, repeatType, priority, taskNotes; // save all var to Task Obj.
    LocalDate dueDate;
    LocalTime dueTime;
    HousemateRecViewAdapter housemateRecViewAdapter;

    NotificationChannel PushNotificationChannel;


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

        housemates = MainActivity.currentHousehold.getUsers();





        repeatTaskSpinner = findViewById(R.id.repeatTaskSpinner);
        ArrayAdapter<CharSequence> repeatTaskAdapter = ArrayAdapter.createFromResource(
                this, R.array.repeat_task_arr, android.R.layout.simple_spinner_item);
        repeatTaskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatTaskSpinner.setAdapter(repeatTaskAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, this.getResources().getStringArray(R.array.repeat_task_arr));
        adapter.setDropDownViewResource(R.layout.spinner_item);
        repeatTaskSpinner.setAdapter(adapter);
        repeatTaskSpinner.setOnItemSelectedListener(this);

        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskName = taskNameET.getText().toString();
                taskNotes = notesET.getText().toString();
                assignHousemates();

                IncompleteTask task = new IncompleteTask(taskName, taskNotes, assignedTo, dueDate, dueTime, repeatType);
                task.create(MainActivity.currentHousehold.getHouseID());



                Intent intent = new Intent(CreateTaskActivity.this, HomePageActivity.class);
                startActivity(intent);



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

        CheckBox assignCheckBox = ( CheckBox ) findViewById( R.id.assignCheckBox );
        assignCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    setUpHousemateRecView();
                    housemateRecView.setVisibility(View.VISIBLE);
                    Arrays.fill(isAssigned, Boolean.TRUE);
                }
                else{
                    housemateRecView.setVisibility(View.GONE);
                    assignedTo.clear();
                    Arrays.fill(isAssigned, Boolean.FALSE);
                }
            }
        });
    }

    @Override
    public void onHousemateClicked(View view, int position, Boolean isChecked) {
        isAssigned[position] = isChecked;
    }

    public void assignHousemates(){
        for(int i =0; i <isAssigned.length; i++){
            if(isAssigned[i]){
                assignedTo.add(housemates.get(i));
            }
        }
    }

    public void setUpHousemateRecView(){
        housemateRecViewAdapter = new HousemateRecViewAdapter(CreateTaskActivity.this);
        housemateRecView.setAdapter(housemateRecViewAdapter);
        housemateRecViewAdapter.setHousemateList(housemates);
        housemateRecViewAdapter.setClickListener(this);
        housemateRecView.setLayoutManager(new LinearLayoutManager(CreateTaskActivity.this));
        isAssigned = new Boolean [housemates.size()];
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // get selected date from date picker dialog
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dueDate = LocalDate.of(year, month + 1, dayOfMonth);
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("jk", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}