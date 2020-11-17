package com.housemate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.housemate.activities.EditProfile;
import com.housemate.activities.MainActivity;
import com.housemate.activities.R;

import java.util.ArrayList;

public class CurrentHouseholdRVAdapter extends RecyclerView.Adapter<CurrentHouseholdRVAdapter.ViewHolder>{
    ArrayList<String> housemateList = new ArrayList<>();
    ArrayList<String> userIdList = new ArrayList<>();
    private Context mContext ;


    public CurrentHouseholdRVAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setHousemateList(ArrayList<String> housemateList) {
        this.housemateList = housemateList;
    }

    public void setUserIdList(ArrayList<String> userIdList) {
        this.userIdList = userIdList;
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
        if (Integer.parseInt(userIdList.get(position)) == MainActivity.currentUser.getId())
        {
            holder.removeHousemateBtn.setVisibility(View.INVISIBLE);
        }

        holder.removeHousemateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userIdStr = userIdList.get(position);
                int userId = Integer.parseInt(userIdStr);
                MainActivity.currentHousehold.removeHousemateFromHousehold(userId);
            }
        });

    }

    @Override
    public int getItemCount() {
        return housemateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout housemateRecView;
        TextView housemateTV;
        Button removeHousemateBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            housemateRecView = itemView.findViewById(R.id.housemateRecView);
            housemateTV = itemView.findViewById(R.id.housemateTV);
            removeHousemateBtn = itemView.findViewById(R.id.removeHousemateBtn);


        }
    }
}

