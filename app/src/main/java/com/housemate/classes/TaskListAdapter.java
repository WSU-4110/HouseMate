package com.housemate.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.housemate.activities.MainActivity;
import com.housemate.activities.R;
import com.housemate.activities.SubtaskDialog;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {
    private final Context context;
    private final List<Task> taskList;
    private final boolean completedTasks; //indicates type of task to be displayed, incomplete or completed
    private itemClickListener listener;

    public class TaskListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{        //static
        public CardView taskCardView;
        public TextView taskNameView;
        public TextView taskDescriptionView;
        public TextView taskDateAndTimeView;
        public TextView assignedUserView;
        public ImageView editTaskView;
        public ImageView deleteTaskView;
        public ImageView completeTaskView;
        public ImageButton subtaskListBtn;

        public TaskListViewHolder(@NonNull View view) {
            super(view);
            taskCardView = view.findViewById(R.id.task_card_view);
            taskNameView = view.findViewById(R.id.task_name_view);
            taskDescriptionView = view.findViewById(R.id.task_description_view);
            taskDateAndTimeView = view.findViewById(R.id.task_date_and_time_view);
            assignedUserView = view.findViewById(R.id.assigned_user_view);
            editTaskView = view.findViewById(R.id.edit_task_view);
            deleteTaskView = view.findViewById(R.id.delete_task_view);
            completeTaskView = view.findViewById(R.id.complete_task_view);
            subtaskListBtn = view.findViewById(R.id.subtask_dialog_button);
            subtaskListBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener != null) {
                listener.onItemClick(v, getAdapterPosition());
            }
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
        String text = String.format("<b>%s</b>", task.getName());
        holder.taskNameView.setText(HtmlCompat.fromHtml(text, 0));
        text = String.format("<b><i>%s</i></b>", task.getDescription());
        holder.taskDescriptionView.setText(HtmlCompat.fromHtml(text, 0));
        holder.taskDateAndTimeView.setText(HtmlCompat.fromHtml(task.getDateAndTimeText(), 0));
        holder.taskCardView.setBackgroundColor((position % 2 == 0) ? 0xFFDDDDDD : 0xFFEEEEEE);

        if (!completedTasks) {
            text = String.format("<i>%s</i>",
                    String.join(", ", ((IncompleteTask) task).getAssignedUsers()));
            holder.assignedUserView.setText(HtmlCompat.fromHtml(text, 0));

                    /*
        holder.editTaskView.setOnClickListener(view -> edit the task);
        */
            holder.completeTaskView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IncompleteTask.complete(task.getId(),MainActivity.currentUser.getId(), MainActivity.currentHousehold.getHouseID());
                    notifyDataSetChanged();
                }
            });
            holder.deleteTaskView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IncompleteTask.delete(task.getId());
                    notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public interface itemClickListener {
        void onItemClick(View view, int position);
    }

    public void setClickListener(itemClickListener listenerItem) {
        this.listener = listenerItem;
    }
}
