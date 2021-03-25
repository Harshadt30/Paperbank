package com.example.paperbank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPhone, etPassword;
    private Button btnSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnSingUp = findViewById(R.id.btnSingUp);

        btnSingUp.setOnClickListener(v-> {

            if(!validateUsername() | !validateEmail() | !validatePhone() | validatePassword()) {

                return;
            }
        });
    }

    private boolean validateUsername() {

        String username = etUsername.getText().toString().trim();
        if(username.isEmpty()) {

            etUsername.setError("Field Can't ba empty");
            return false;
        }
        else {

            etUsername.setError(null);
            return true;
        }
    }
    private boolean validateEmail() {

        String email = etEmail.getText().toString().trim();
        if(email.isEmpty()) {

            etEmail.setError("Field Can't ba empty");
            return false;
        }
        else {

            etEmail.setError(null);
            return true;
        }
    }
    private boolean validatePhone() {

        String phone = etPhone.getText().toString().trim();
        if(phone.isEmpty()) {

            etPhone.setError("Field Can't ba empty");
            return false;
        }
        else {

            etPhone.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {

        String password = etPassword.getText().toString().trim();
        if(password.isEmpty()) {

            etPassword.setError("Field Can't ba empty");
            return false;
        }
        else {

            etPassword.setError(null);
            return true;
        }
    }
}