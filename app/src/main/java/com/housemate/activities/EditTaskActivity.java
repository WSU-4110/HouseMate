package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.housemate.classes.DiscardTaskDialogue;
import com.housemate.adapters.HousemateRecViewAdapter;
import com.housemate.classes.IncompleteTask;

import java.util.ArrayList;
import java.util.Arrays;

public class EditTaskActivity extends AppCompatActivity implements HousemateRecViewAdapter.CheckBoxListener {
    EditText taskNameET, notesET;
    Button updateTaskBtn, cancelBtn;
    static ArrayList<String> housemates;
    ArrayList<String> assignedTo;
    Boolean[] isAssigned;
    RecyclerView housemateRecView;
    String taskName, taskNotes;
    HousemateRecViewAdapter housemateRecViewAdapter;
    IncompleteTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String taskJson = getIntent().getStringExtra("taskJson");
        try {
            task = objectMapper.readValue(taskJson, IncompleteTask.class);
        }
        catch (Exception e) {
            throw new RuntimeException("Error processing task");
        }

        taskNameET = findViewById(R.id.taskNameET);
        updateTaskBtn = findViewById(R.id.updateTaskBtn);
        housemateRecView = findViewById(R.id.housemateRecView);
        assignedTo = new ArrayList<>();
        housemates = MainActivity.currentHousehold.getUsers();
        isAssigned = new Boolean [housemates.size()];
        Arrays.fill(isAssigned, Boolean.FALSE);
        notesET = findViewById(R.id.notesET);
        cancelBtn = findViewById(R.id.cancelBtn);
        taskName = task.getName();
        taskNotes = task.getDescription();

        taskNameET.setText(taskName);
        notesET.setText(taskNotes);

        updateTaskBtn.setOnClickListener(v -> {
            assignHousemates();

            task.setName(taskNameET.getText().toString());
            task.setDescription(notesET.getText().toString());
            task.setAssignedUsers(assignedTo);
            task.update();

            Intent intent = new Intent(EditTaskActivity.this, HomePageActivity.class);
            startActivity(intent);
        });

        //change text?
        cancelBtn.setOnClickListener(v -> {
            DiscardTaskDialogue discardTaskDialogue = new DiscardTaskDialogue();
            discardTaskDialogue.show(getSupportFragmentManager(), "discardTaskDialogue");
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

    public void onCheckboxClicked(View view) {
        CheckBox housemateCheckBox = view.findViewById(R.id.housemateCheckBox);
        String selectedHousemate = housemateCheckBox.getText().toString();

        boolean checked = housemateCheckBox.isChecked();
        if (checked) {
            assignedTo.add(selectedHousemate);
            Log.i("selected", selectedHousemate);
        }
        else {
            for (int i = 0; i < assignedTo.size(); i++){
                if (assignedTo.get(i).equals(selectedHousemate)){
                    assignedTo.remove(selectedHousemate);
                }
            }
            Log.i("unselected", selectedHousemate);
        }
    }

    private void setUpHousemateRecView() {
        housemateRecViewAdapter = new HousemateRecViewAdapter(EditTaskActivity.this);
        housemateRecView.setAdapter(housemateRecViewAdapter);
        housemateRecViewAdapter.setHousemateList(housemates);
        housemateRecViewAdapter.setClickListener(this);
        housemateRecView.setLayoutManager(new LinearLayoutManager(EditTaskActivity.this));
        isAssigned = new Boolean [housemates.size()];
    }
}
