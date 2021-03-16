package com.example.paperbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SubjectActivity extends AppCompatActivity {

    private com.google.android.material.appbar.MaterialToolbar subjectTopBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        subjectTopBar = findViewById(R.id.subjectTopBar);
        Intent intent = getIntent();
        String courseName = intent.getStringExtra("Course");
        subjectTopBar.setTitle(courseName);
    }
}