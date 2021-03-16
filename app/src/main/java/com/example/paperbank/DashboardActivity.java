package com.example.paperbank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private ListView lstCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String[] courses = { "BCA", "BBA", "BCom" };

        CourseAdapter courseAdapter = new CourseAdapter(this, courses);
        lstCourse = findViewById(R.id.lstCourse);
        lstCourse.setAdapter(courseAdapter);

    }
}