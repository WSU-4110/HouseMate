package com.housemate.classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.housemate.activities.CreateTaskActivity;
import com.housemate.activities.R;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {
    private Context context;
    private List<Task> taskList;

    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        public TextView taskView;
        public TextView assignedUserView;
        public ImageView editTaskView;
        public ImageView deleteTaskView;

        public TaskListViewHolder(@NonNull View view) {
            super(view);
            taskView = view.findViewById(R.id.task_text);
            assignedUserView = view.findViewById(R.id.assigned_user);
            editTaskView = view.findViewById(R.id.edit_task_view);
            deleteTaskView = view.findViewById(R.id.delete_task_view);
        }
    }

    public TaskListAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    @NonNull
    public TaskListAdapter.TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.task_in_list, parent, false);
        return new TaskListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskListViewHolder holder, int position) {
        Task task = taskList.get(position);
        String taskText = String.format("<h3>%s</h3>" +
                                    "<b><i>%s</i></b>" +
                                    "<p>Due %s at %s</p>\n",
                                    task.getName(), task.getDescription(), task.getDueDate(), task.getDueTime());
        holder.taskView.setText(HtmlCompat.fromHtml(taskText, 0));
        holder.taskView.setBackgroundColor((position % 2 == 0) ? 0xFFDDDDDD : 0xFFEEEEEE);

        String userText = String.format("<i>%s</i>", task.getAssignedUser());
        holder.assignedUserView.setText(HtmlCompat.fromHtml(userText, 0));
/*
        holder.editTaskView.setOnClickListener(view -> context.startActivity(
                new Intent(context, EditTaskActivity.class)));
        holder.deleteTaskView.setOnClickListener(view -> context.startActivity(
                new Intent(context, DeleteTaskActivity.class)));
 */
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
