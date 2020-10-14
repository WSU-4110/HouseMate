package com.housemate.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HousemateRecViewAdapter extends RecyclerView.Adapter<HousemateRecViewAdapter.ViewHolder>{
    ArrayList<String> housemateList = new ArrayList<>();
    private Context mContext ;



    public HousemateRecViewAdapter(Context mContext) {
        this.mContext = mContext;


    }

    public void setHousemateList(ArrayList<String> housemateList) {
        this.housemateList = housemateList;
        // default all housemates assigned to task
    }


    @NonNull
    @Override
    public HousemateRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.housemate_rec_view_detail,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HousemateRecViewAdapter.ViewHolder holder, int position) {
        holder.housemateCheckBox.setText(housemateList.get(position));



    }

    @Override
    public int getItemCount() {
        return housemateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout housemateRecView;
        CheckBox housemateCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            housemateRecView = itemView.findViewById(R.id.housemateRecView);
             housemateCheckBox= itemView.findViewById(R.id.housemateCheckBox);
        }
    }
}

