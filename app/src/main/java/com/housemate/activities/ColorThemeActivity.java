package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class ColorThemeActivity extends AppCompatActivity {
    RadioButton lightBtn, darkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_theme);
        lightBtn = (RadioButton) findViewById(R.id.lightTheme);
        darkBtn = (RadioButton) findViewById(R.id.darkTheme);
    }

    public void backToSettings(View view) {
        startActivity(new Intent(this, Settings.class));
    }
}