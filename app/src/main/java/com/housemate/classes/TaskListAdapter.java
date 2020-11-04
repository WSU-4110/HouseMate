package com.housemate.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.housemate.activities.R;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {
    private final Context context;
    private final List<Task> taskList;
    private final boolean completedTasks;

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

    public TaskListAdapter(Context context, List<Task> taskList, boolean completedTasks) {
        this.context = context;
        this.taskList = taskList;
        this.completedTasks = completedTasks;
    }

    @Override
    @NonNull
    public TaskListAdapter.TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = (completedTasks) ? R.layout.completed_task : R.layout.incomplete_task;
        View view = layoutInflater.inflate(layoutId, parent, false);
        //view.setOnClickListener(v -> view the task);
        return new TaskListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskListViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskView.setText(HtmlCompat.fromHtml(task.toString(), 0));

        if (task instanceof IncompleteTask) {
            String assignedUsersText = String.format("<i>%s</i>",
                    String.join(", ", ((IncompleteTask) task).getAssignedUsers()));
            holder.assignedUserView.setText(HtmlCompat.fromHtml(assignedUsersText, 0));
        }

        holder.taskView.setBackgroundColor((position % 2 == 0) ? 0xFFDDDDDD : 0xFFEEEEEE);

        /*
        holder.editTaskView.setOnClickListener(view -> edit the task);
        holder.completeTaskView.setOnClickListener(view -> complete the task);
        holder.deleteTaskView.setOnClickListener(view -> delete the task);
    */
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
