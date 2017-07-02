package com.example.eduard.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.button;

public class LoginScreen extends AppCompatActivity {

    boolean emailHasContent;
    boolean passwordHasContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button loginButton = (Button) findViewById(R.id.button_login);
        final EditText email = (EditText) findViewById(R.id.input_login_email);
        final EditText password = (EditText) findViewById(R.id.input_login_password);
        final TextView errorMessage = (TextView) findViewById(R.id.Login_errorMessage);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_progressBar);

        loginButton.setEnabled(false);
        progressBar.setVisibility(View.INVISIBLE);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(errorMessage.getText().toString().startsWith("email")) {
                    errorMessage.setText("");
                }
                if(email.getText().length() > 0) {
                    emailHasContent = true;
                    if(passwordHasContent)
                        loginButton.setEnabled(true);
                } else {
                    emailHasContent = false;
                    loginButton.setEnabled(false);
                }
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    errorMessage.setText((!CheckWidgetContent.checkEmail(email.getText().toString()))?"email-address is not well formed!":"");
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(errorMessage.getText().toString().startsWith("password")) {
                    errorMessage.setText("");
                }
                if(password.getText().length() > 0) {
                    passwordHasContent = true;
                    if(emailHasContent)
                        loginButton.setEnabled(true);
                } else {
                    passwordHasContent = false;
                    loginButton.setEnabled(false);
                }
            }
        });
        final Intent intentToOverview = new Intent(this, Overview.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(errorMessage.getText().equals("")){
                    if(CheckWidgetContent.checkEmail(email.getText().toString()) && CheckWidgetContent.checkPassword(password.getText().toString())) {
                        Toast.makeText(LoginScreen.this, "Login successful " + email.getText() + "; " + password.getText(), Toast.LENGTH_LONG).show();
                        startActivity(intentToOverview);
                    } else {
                        errorMessage.setText("password is not well formed!");
                    }
                }
            }
        });
    }
}
