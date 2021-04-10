package com.example.paperbank;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.security.auth.Subject;

public class SubjectActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private MaterialToolbar toolbar;
    private ListView subjectListview;
    private ArrayList<Subjects> subject;
    private SubjectAdapter subjectAdapter;
    private TextView home, course;
    private PaperbankDatabaseHelper db;
    private TextView userMail, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        subjectListview = findViewById(R.id.subjectListview);
        home = findViewById(R.id.home);
        course = findViewById(R.id.course);

        home.setOnClickListener(v -> { 

            Intent intent = new Intent(SubjectActivity.this, DashboardActivity.class);
            startActivity(intent);
        });

        course.setOnClickListener(v-> { finish(); });
        db = new PaperbankDatabaseHelper(this);

        Cursor cursor = db.userRow();
        if(cursor.moveToNext()) {

            View navView = nav_view.getHeaderView(0);
            userMail = navView.findViewById(R.id.userMail);
            username = navView.findViewById(R.id.username);
            userMail.setText(cursor.getString(2));
            username.setText(cursor.getString(1));
        }

        Intent intent = getIntent();
        String courseID = intent.getStringExtra("course_id");
        String courseNAME = intent.getStringExtra("course_name");
        String courseDETAIL = intent.getStringExtra("course_detail");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> { drawerLayout.openDrawer(GravityCompat.START); });

        UrlPaperbank urlPaperbank = new UrlPaperbank();
        subject = new ArrayList<>();
        subjectAdapter = new SubjectAdapter(this, subject);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPaperbank.URl + "subject.php?course_id=" + courseID, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

//                    Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Subjects subjects = new Subjects();

                        subjects.setCourse_id(courseID);
                        subjects.setCourse_name(courseNAME);
                        subjects.setCourse_detail(courseDETAIL);
                        subjects.setSubject_id(jsonObject.getString("subject_id"));
                        subjects.setSubject_name(jsonObject.getString("subject_name"));
                        subjects.setSubject_detail(jsonObject.getString("subject_detail"));

                        subject.add(subjects);
                    }

                    subjectListview.setAdapter(subjectAdapter);

                    subjectListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            TextView subject_id = view.findViewById(R.id.subject_id);
                            TextView subject_name = view.findViewById(R.id.subject_name);
                            String subjectID = subject_id.getText().toString();
                            String subjectNAME = subject_name.getText().toString();

                            Intent intent = new Intent(SubjectActivity.this, PaperActivity.class);
                            intent.putExtra("subject_id", subjectID);
                            intent.putExtra("subjec_name", subjectNAME);
                            startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

        nav_view.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.nav_home:

                    Intent intent2 = new Intent(SubjectActivity.this, DashboardActivity.class);
                    startActivity(intent2);
                    finish();
                    break;

                case R.id.nav_profile:

                    Intent intent1 = new Intent(SubjectActivity.this, ProfileActivity.class);
                    startActivity(intent1);
                    finish();
                    break;

                case R.id.nav_paper :
                    break;

                case R.id.nav_email:

                    Toast.makeText(getApplicationContext(), "Visit 192.168.0.111/help/  For more info", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.logOUT:

                    if (db.removeUser()) {
                        Intent logOut = new Intent(SubjectActivity.this, LoginActivity.class);
                        startActivity(logOut);
                        finish();

                    } else {
                        finishAffinity();
                        System.exit(0);
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            return true;
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }
}