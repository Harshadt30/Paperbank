package com.example.paperbank;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnLogIn, btnSingUp;
    private PaperbankDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new PaperbankDatabaseHelper(this);
        Cursor cursor = db.userRow();

        if (cursor.getCount() == 1) {

            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogIn = findViewById(R.id.btnLogIn);
        btnSingUp = findViewById(R.id.btnSingUp);

        btnLogIn.setOnClickListener(v -> {

            Intent logIn = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logIn);
            finish();
        });

        btnSingUp.setOnClickListener(v -> {

            Intent signUp = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(signUp);
            finish();
        });
    }
}