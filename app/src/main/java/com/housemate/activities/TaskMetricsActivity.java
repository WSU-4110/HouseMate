package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TaskMetricsActivity extends AppCompatActivity {
    private TextView metricsTV;
    private TextView metricNumsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_metrics);
        metricsTV = (TextView) findViewById(R.id.metricsTV);
        metricNumsTV = (TextView) findViewById(R.id.metricNumsTV);
        ArrayList<ArrayList<String>> metrics = MainActivity.currentHousehold.loadMetrics();
        String text = "";
        for (int i = 0; i < metrics.size(); i++) {
            text += metrics.get(i).get(0) + "\n";
        }
        metricsTV.setText(text);
        text = "";
        for (int i = 0; i < metrics.size(); i++) {
            text += metrics.get(i).get(1) + "\n";
        }
        metricNumsTV.setText(text);

    }
}