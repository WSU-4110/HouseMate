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
import java.util.List;

public class TaskRecViewAdapter extends RecyclerView.Adapter<TaskRecViewAdapter.ViewHolder>{
    List<String> taskList = new ArrayList<>();
    private Context mContext ;

    public TaskRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setTaskList(List<String> taskList) {
        this.taskList = taskList;
    }


    @NonNull
    @Override
    public TaskRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_listview_detail,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecViewAdapter.ViewHolder holder, int position) {
        holder.checkBox.setText(taskList.get(position));
        holder.dueDateTV.setText("Due by -----");
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout taskRecView;
        CheckBox checkBox;
        TextView dueDateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskRecView = itemView.findViewById(R.id.taskRecView);
            checkBox = itemView.findViewById(R.id.checkbox);
            dueDateTV = itemView.findViewById(R.id.dueDateTV);
        }
    }
}

