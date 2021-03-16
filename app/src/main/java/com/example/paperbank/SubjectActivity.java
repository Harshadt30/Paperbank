package com.example.paperbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class SubjectActivity extends AppCompatActivity {

    private com.google.android.material.appbar.MaterialToolbar subjectTopBar;
    private ListView lstSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        String[] subjectArray = {"PHP", "JS", "REACT-JS", "JAVA", "C", "C++", "ASP.net", "C#", "PYTHON", "HTML"};

        lstSubject = findViewById(R.id.lstSubject);
        subjectTopBar = findViewById(R.id.subjectTopBar);
        Intent intent = getIntent();
        String courseName = intent.getStringExtra("Course");
        subjectTopBar.setTitle(courseName);


        SubjectAdapter subjectAdapter = new SubjectAdapter(this, subjectArray);
        lstSubject.setAdapter(subjectAdapter);

    }
}