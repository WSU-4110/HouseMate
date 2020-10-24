package com.housemate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.housemate.activities.R;

import java.util.ArrayList;

public class CurrentHouseholdRVAdapter extends RecyclerView.Adapter<CurrentHouseholdRVAdapter.ViewHolder>{
    ArrayList<String> housemateList = new ArrayList<>();
    private Context mContext ;


    public CurrentHouseholdRVAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setHousemateList(ArrayList<String> housemateList) {
        this.housemateList = housemateList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_household_rv_detail,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.housemateTV.setText(housemateList.get(position));
    }



    @Override
    public int getItemCount() {
        return housemateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout housemateRecView;
        TextView housemateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            housemateRecView = itemView.findViewById(R.id.housemateRecView);
            housemateTV = itemView.findViewById(R.id.housemateTV);
        }
    }
}

