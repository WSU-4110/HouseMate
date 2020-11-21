package com.housemate.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RenameHouseFragment extends Fragment {
    private RenameHouseFragmentListener listener;
    private Button cancelBtn, saveBtn;
    private EditText renameHouseET;
    private String houseName;

    public interface RenameHouseFragmentListener{
        void onHouseNameSent(CharSequence houseName);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.rename_household_fragment, container, false);

       cancelBtn = (Button)v.findViewById(R.id.cancelBtn);
       saveBtn = (Button)v.findViewById(R.id.saveBtn);
       renameHouseET= (EditText)v.findViewById(R.id.renameHouseET);

       if(houseName != null){
           renameHouseET.setText(houseName);
       }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence houseName = renameHouseET.getText();
                listener.onHouseNameSent(houseName);
                getFragmentManager().beginTransaction().remove(RenameHouseFragment.this).commit();

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(RenameHouseFragment.this).commit();
            }
        });
       return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  RenameHouseFragmentListener){ // context is activity
            listener = (RenameHouseFragmentListener)context;
        }
        else{
            throw new RuntimeException(context.toString()
            + " must implement RenameHouseFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        hideKeyBoard();
    }

    // send data from activity to this fragment
    public void setHouseName(String originalHouseName){
        houseName = originalHouseName;
    }

    public void hideKeyBoard(){
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }
}
