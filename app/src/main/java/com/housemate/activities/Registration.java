package com.housemate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.housemate.classes.User;

public class Registration extends AppCompatActivity implements View.OnFocusChangeListener  {
    EditText firstNameET;
    EditText lastNameET;
    EditText emailET;
    EditText usernameET;
    EditText passwordET;
    EditText confirmPasswordET;
    TextView emailTV, firstNameTV, lastNameTV, usernameTV, passwordTV, confirmPasswordTV, passwordErrorTV, emailErrorTV;
    EditText [] inputFieldsET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration3);
        firstNameET = (EditText)findViewById(R.id.firstName);
        lastNameET = (EditText)findViewById(R.id.lastName);
        emailET = (EditText)findViewById(R.id.Email);
        usernameET = (EditText)findViewById(R.id.edUserName);
        passwordET = (EditText)findViewById(R.id.enterPass);
        confirmPasswordET = (EditText)findViewById(R.id.Confirm);
        emailTV = findViewById(R.id.EmailView);
        firstNameTV = findViewById(R.id.enterFname);
        lastNameTV = findViewById(R.id.enterLname);
        usernameTV = findViewById(R.id.uName);
        passwordTV = findViewById(R.id.pass);
        confirmPasswordTV = findViewById(R.id.ReEnter);
        passwordErrorTV = findViewById(R.id.passwordErrorTV);
        emailErrorTV = findViewById(R.id.emailErrorTV);


        firstNameET.setOnFocusChangeListener(this);
        lastNameET.setOnFocusChangeListener(this);
        emailET.setOnFocusChangeListener(this);
        usernameET.setOnFocusChangeListener(this);
        passwordET.setOnFocusChangeListener(this);
        confirmPasswordET.setOnFocusChangeListener(this);

        inputFieldsET  =  new EditText[]{firstNameET, lastNameET, emailET, usernameET, passwordET, confirmPasswordET};
    }


    // Execute new user insert to database
    public void register (View view) {

        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String email = emailET.getText().toString();
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        String confirm = confirmPasswordET.getText().toString();

        setStyleOfInputLabelTV(firstNameTV, firstNameET);
        setStyleOfInputLabelTV(lastNameTV, lastNameET);
        setStyleOfEmailInputLabelTV();
        setStyleOfInputLabelTV(usernameTV, usernameET);
        setStyleOfBothPasswordInputLabels(password, confirm);

        int numEmptyFields = countEmptyFields(inputFieldsET);

        if(numEmptyFields != 0 || !password.equals(confirm) || !isEmailValid(email)){
            Context context = getApplicationContext();
            CharSequence text = "Registration failed!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {

            MainActivity.currentUser.setUser_name(username);
            MainActivity.currentUser.setUser_pass(password);
            MainActivity.currentUser.setEmail(email);
            MainActivity.currentUser.setFirstName(firstName);
            MainActivity.currentUser.setLastName(lastName);
            MainActivity.currentUser.register();


            Intent intent = new Intent(this, JoinCreateHousehold.class);
            startActivity(intent);
        }
    }

    public void onBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    int countEmptyFields(EditText [] inputFieldsET){
        int numEmptyFields = 0;
        for(int i =0; i < inputFieldsET.length ; i++) {
            if(inputFieldsET[i].getText().toString().isEmpty()){
                numEmptyFields++;
            }
        }
        return numEmptyFields;
    }
    void setStyleOfBothPasswordInputLabels(String password, String confirmPassword){
        if(password.equals(confirmPassword)){
            passwordErrorTV.setVisibility(View.GONE);
            setInputLabelToDefaultStyle(passwordTV);
            setInputLabelToDefaultStyle(confirmPasswordTV);
        }
        else{
            passwordErrorTV.setVisibility(View.VISIBLE);
            markInputLabelAsRequired(passwordTV);
            markInputLabelAsRequired(confirmPasswordTV);
            confirmPasswordET.setText("");
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    void setStyleOfEmailInputLabelTV(){
        if(isEmailValid(emailET.getText().toString())){
            emailErrorTV.setVisibility(View.GONE);
            setInputLabelToDefaultStyle(emailTV);
        }
        else{
            emailErrorTV.setVisibility(View.VISIBLE);
            markInputLabelAsRequired(emailTV);
        }
    }

    public void setStyleOfInputLabelTV(TextView inputLabelTV, EditText inputField ){
        if(inputField.getText().toString().isEmpty()) {
            markInputLabelAsRequired(inputLabelTV);
        }
        else{
            setInputLabelToDefaultStyle(inputLabelTV);
        }
    }

    public void markInputLabelAsRequired(TextView inputLabelTV){
        String inputTitle = inputLabelTV.getText().toString();
        inputTitle = inputTitle.replace("*","");

        inputTitle += "*";
        inputLabelTV.setText(inputTitle);
        inputLabelTV.setTextColor(getResources().getColor( R.color.error_red, this.getTheme()));
    }

    public void setInputLabelToDefaultStyle(TextView inputLabelTV){
        String inputTitle = inputLabelTV.getText().toString();
        inputTitle = inputTitle.replace("*","");

        inputLabelTV.setText(inputTitle);
        inputLabelTV.setTextColor(getResources().getColor( R.color.black_800, this.getTheme()));
    }



    /* each case in the switch statement only executes when the cursor leaves the editText View.
            hasFocus is true when cursor is in the editText View. */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (!hasFocus) {
            switch (v.getId()) {
                case R.id.firstName:
                    setStyleOfInputLabelTV(firstNameTV, firstNameET);
                    break;
                case R.id.lastName:
                    setStyleOfInputLabelTV(lastNameTV, lastNameET);
                    break;
                case R.id.Email:
                    setStyleOfEmailInputLabelTV();
                    break;
                case R.id.edUserName:
                    setStyleOfInputLabelTV(usernameTV, usernameET);
                    break;
                case R.id.enterPass:
                    setStyleOfInputLabelTV(passwordTV, passwordET);
                    break;
                case R.id.Confirm:
                    setStyleOfInputLabelTV(confirmPasswordTV, confirmPasswordET);
                    break;
            }
        }
    }
}