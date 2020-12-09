package com.housemate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.housemate.activities.CreateTaskActivity;
import com.housemate.activities.R;

import java.util.ArrayList;

public class HousemateRecViewAdapter extends RecyclerView.Adapter<HousemateRecViewAdapter.ViewHolder> {
    ArrayList<String> housemateList = new ArrayList<>();
    private Context mContext ;
    private HousemateRecViewAdapter.CheckBoxListener mCheckBoxListener;


    public HousemateRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setHousemateList(ArrayList<String> housemateList) {
        this.housemateList = housemateList;
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
        for(int i =0; i<this.getItemCount() -1; i++){
            holder.housemateCheckBox.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return housemateList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout housemateRecView;
        CheckBox housemateCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            housemateRecView = itemView.findViewById(R.id.housemateRecView);
            housemateCheckBox= itemView.findViewById(R.id.housemateCheckBox);
            housemateCheckBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mCheckBoxListener != null ) {
                mCheckBoxListener.onHousemateClicked(v, getAdapterPosition(), housemateCheckBox.isChecked());
            }
        }
    }
    public void setClickListener(CheckBoxListener checkBoxListener) {
        this.mCheckBoxListener = checkBoxListener;
    }

    // CreateTaskActivity will implement this method to respond to when housemates are selected/unselected
    public interface CheckBoxListener {
        void onHousemateClicked(View view, int position, Boolean isChecked);
    }

}

