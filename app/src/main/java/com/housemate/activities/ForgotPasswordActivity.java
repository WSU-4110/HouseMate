package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.housemate.classes.User;

public class ForgotPasswordActivity extends AppCompatActivity {
    private TextInputEditText emailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailET = (TextInputEditText) findViewById(R.id.emailInputTextFieldET);
    }


    public void requestPasswordReset(View view) {
        String email = emailET.getText().toString();
        if (!email.equals("")) {
            int result = User.requestPasswordReset(email);
            if (result == 1) {
                Snackbar.make(view, R.string.password_reset_email_sent, Snackbar.LENGTH_LONG).show();
            }
            else {
                Snackbar.make(view, R.string.error, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public void gotoMainActivity(View view) {

        startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
    }
}