package com.example.paperbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        lstCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String course = (String)parent.getItemAtPosition(position);
                Intent subjectActivity = new Intent(DashboardActivity.this, SubjectActivity.class);
                subjectActivity.putExtra("Course", course);
                startActivity(subjectActivity);
            }
        });
    }
}