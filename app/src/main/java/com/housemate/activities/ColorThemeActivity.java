package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class ColorThemeActivity extends AppCompatActivity {
    Button backBtn;
    RadioButton lightBtn, darkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_theme);
        backBtn = (Button) findViewById(R.id.back_to_settings);
        lightBtn = (RadioButton) findViewById(R.id.lightTheme);
        darkBtn = (RadioButton) findViewById(R.id.darkTheme);

        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
            }
        });
    }
}