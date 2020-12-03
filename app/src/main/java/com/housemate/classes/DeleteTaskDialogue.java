package com.housemate.classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteTaskDialogue extends AppCompatDialogFragment {
    private final IncompleteTask task;
    private final TaskListAdapter adapter;

    public DeleteTaskDialogue(IncompleteTask task, TaskListAdapter adapter) {
        this.task = task;
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm task deletion");

        builder.setNegativeButton("Cancel", (dialog, which) -> {});

        builder.setPositiveButton("Continue", (dialog, which) -> {
            IncompleteTask.delete(task.getId());
            adapter.notifyDataSetChanged();
        });

        return builder.create();
    }
}
