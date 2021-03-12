package com.example.paperbank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogIn = findViewById(R.id.btnLogIn);


        btnLogIn.setOnClickListener(v-> {

            if(!validateUsername() | !validatePassword()) {
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