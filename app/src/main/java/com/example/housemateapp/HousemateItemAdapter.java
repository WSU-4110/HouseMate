package com.example.housemateapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class HousemateItemAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    ArrayList<String> housemates;
    public HousemateItemAdapter(Context c,ArrayList<String> h){
        housemates = h;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return housemates.size();
    }

    @Override
    public Object getItem(int position) {
        return housemates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.housemate_listview_detail, null);
        CheckBox checkbox_housemate = v.findViewById(R.id.checkbox_housemate);
        checkbox_housemate.setText(housemates.get(position));

        return v;
    }
}
