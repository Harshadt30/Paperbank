package com.example.paperbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnLogIn, btnSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSingUp = findViewById(R.id.btnSingUp);

        btnLogIn.setOnClickListener(v-> {

            Intent logIn = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logIn);
            finish();
        });

        btnSingUp.setOnClickListener(v-> {

            Intent signUp = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(signUp);
            finish();
        });
    }
}