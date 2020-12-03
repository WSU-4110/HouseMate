package com.housemate.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TaskInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.housemate.activities.HomePageActivity;

import com.housemate.activities.R;
import com.housemate.classes.Subtask;
import com.housemate.classes.Task;

public class SubtaskDialog extends AppCompatDialogFragment {
    private Subtask[] subtaskItems; // = "loadSubtasks.php";
    private Task parentTask;
    private boolean selectedItem;

    private TextView subtaskName;
    private ImageView subtaskStatusImage;

    @Override @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater subtaskInflater = getActivity().getLayoutInflater();
        View view = subtaskInflater.inflate(R.layout.subtask_dialog_list, null);

        AlertDialog.Builder subtaskBuilder = new AlertDialog.Builder(getActivity());
        subtaskBuilder.setView(view);
        subtaskBuilder.setTitle(parentTask.getName()); //Set title based on respective listView Task object button pressed
        subtaskBuilder.setMessage(R.string.subtask_message);

        subtaskBuilder.setPositiveButton(R.string.subtask_complete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Selected subtask's picture changes to 'check', task is now non-editable
            }
        });
        subtaskBuilder.setNegativeButton(R.string.subtask_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Window will close
            }
        });
        subtaskBuilder.setNeutralButton(R.string.subtask_edit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Change the description of the subtask, or delete it
                //subtaskName = view.findViewById(R.id.subtask_name_edit_text)
            }
        });

        return subtaskBuilder.create();
    }
}