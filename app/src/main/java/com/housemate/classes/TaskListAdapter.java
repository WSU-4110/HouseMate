package com.housemate.classes;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.housemate.activities.R;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {
    private ArrayList<Task> taskList;

    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        public TextView taskView;

        public TaskListViewHolder(TextView taskView) {
            super(taskView);
            this.taskView = taskView;
        }
    }

    public TaskListAdapter(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    @NonNull
    public TaskListAdapter.TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TextView taskView = (TextView) layoutInflater.inflate(R.layout.task_text_view, parent, false);
        return new TaskListViewHolder(taskView);
    }

    @Override
    public void onBindViewHolder(TaskListViewHolder holder, int position) {
        holder.taskView.setText(taskList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
