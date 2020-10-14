
package com.housemate.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.housemate.activities.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardRecViewAdapter extends RecyclerView.Adapter<CardRecViewAdapter.ViewHolder>{
    // TODO: bug - when click expand btn many times, task card remains collapsed

    List<String> priorityLevelList = new ArrayList<>(); // contains title for the TV of each priority card
    List<List<String>> taskList = new ArrayList<>();  // list of tasks organized to 3 lists (low, medium, high)
    List<Boolean> isExpanded; // priority cards can expand (showing all tasks) or collapse (showing top few tasks)

    private Context mContext;

    public CardRecViewAdapter(Context mContext) {
        this.mContext = mContext;
        this.isExpanded =new ArrayList<>(Arrays.asList(new Boolean[3]));
        Collections.fill(isExpanded, Boolean.FALSE);
    }

    public void setPriorityLevelList(List<String> priorityLevelList) {
        this.priorityLevelList = priorityLevelList;
    }

    public void setTaskList(List<List<String>> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.priority_level_card,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        holder.priorityLevelTV.setText(priorityLevelList.get(position));

        // set up parent recycler view (top recycler view of card)
        // fill RV with first few task items via RV adapter
        holder.parentRecView.setAdapter(holder.taskRecViewAdapter);
        holder.taskRecViewAdapter.setTaskList(taskList.get(position).subList(0, 2));
        holder.parentRecView.setLayoutManager(new LinearLayoutManager(mContext));

        // set up expanded RV (bot RV of card)
        // fill RV with rest of task items
        holder.expRecView.setAdapter(holder.expTaskRecViewAdapter);
        if(isExpanded.get(position)){
            holder.expRelLayout.setVisibility(View.VISIBLE);
            holder.expandBtn.setImageResource(R.mipmap.ic_up_arrow);
        }
        holder.expTaskRecViewAdapter.setTaskList(taskList.get(position).subList(2, taskList.get(position).size()));
        holder.expRecView.setLayoutManager(new LinearLayoutManager(mContext));
//        else{
//            TransitionManager.beginDelayedTransition(holder.parent);
//            holder.expandedParent.setVisibility(View.GONE);
//            holder.downArrowIV.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public int getItemCount() {
        return priorityLevelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardParent;
        RecyclerView parentRecView, expRecView;
        TextView priorityLevelTV;
        TaskRecViewAdapter taskRecViewAdapter, expTaskRecViewAdapter;
        ImageButton expandBtn;
        RelativeLayout expRelLayout;

        // initialize card view elements
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardParent = itemView.findViewById(R.id.cardParent);
            parentRecView = itemView.findViewById(R.id.parentRecView);
            priorityLevelTV = itemView.findViewById(R.id.priorityLevelTV);
            taskRecViewAdapter = new TaskRecViewAdapter(mContext);
            expTaskRecViewAdapter = new TaskRecViewAdapter(mContext);
            expandBtn = itemView.findViewById(R.id.expandBtn);
            expRelLayout = itemView.findViewById(R.id.expRelLayout);
            expRecView = itemView.findViewById(R.id.expRecView);

            expandBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isExpanded.set(getAdapterPosition(), !isExpanded.get(getAdapterPosition()));
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}


