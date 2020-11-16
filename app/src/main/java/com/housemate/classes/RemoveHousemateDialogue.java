package com.housemate.classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.housemate.activities.CurrentHousehold;
import com.housemate.activities.HomePageActivity;
import com.housemate.adapters.CurrentHouseholdRVAdapter;

public class RemoveHousemateDialogue extends AppCompatDialogFragment  {

    private RemoveHousemateDialogueListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to remove this housemate from the household?");
        builder .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.applyDialogueResponse(false);

            }
        });

        builder.setPositiveButton("Remove housemate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.applyDialogueResponse(true);
            }
        });
        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (RemoveHousemateDialogueListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException((context.toString() + "must implement RemoveHousemateDialogueListener"));
        }
    }

    public interface RemoveHousemateDialogueListener{
        void applyDialogueResponse(Boolean response);
    }
}


