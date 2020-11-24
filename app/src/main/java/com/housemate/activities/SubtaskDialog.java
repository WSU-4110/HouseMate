package com.housemate.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.housemate.activities.HomePageActivity;

import com.housemate.activities.R;
import com.housemate.classes.Subtask;
import com.housemate.classes.Task;

public class SubtaskDialog extends AppCompatDialogFragment {
    Subtask[] subtaskItems; // = "loadSubtasks.php";
    Task parentTask;
    boolean selectedItem;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialAlertDialogBuilder subtaskBuilder = new MaterialAlertDialogBuilder(getActivity());
                subtaskBuilder.setTitle(parentTask.getName()); //Set title based on respective listView Task object button pressed
                subtaskBuilder.setMessage(R.string.subtask_message);

                LayoutInflater subtaskInflater = getLayoutInflater();
                //View dialogView = subtaskInflater.inflate(R.layout.)

        return subtaskBuilder.create();
        }
    }