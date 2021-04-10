package com.example.paperbank;

import android.content.Context;
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

public class CourseActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private MaterialToolbar toolbar;
    private ListView courseListview;
    private Context context;
    private ArrayList<Courses> courses;
    private CourseAdapter courseAdapter;
    private TextView home;
    private PaperbankDatabaseHelper db;
    private TextView userMail, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        courseListview = findViewById(R.id.courseListview);
        home = findViewById(R.id.Home);

        db = new PaperbankDatabaseHelper(this);
        Cursor cursor = db.userRow();
        if(cursor.moveToNext()) {

            View navView = nav_view.getHeaderView(0);
            userMail = navView.findViewById(R.id.userMail);
            username = navView.findViewById(R.id.username);
            userMail.setText(cursor.getString(2));
            username.setText(cursor.getString(1));
        }
        home.setOnClickListener(v -> { finish(); });

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        courses = new ArrayList<>();
        courseAdapter = new CourseAdapter(this, courses);
        UrlPaperbank urlPaperbank = new UrlPaperbank();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPaperbank.URl + "course.php", response -> {

            try {

                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Courses course = new Courses();

                    course.setId(jsonObject.getString("course_id"));
                    course.setName(jsonObject.getString("course_name"));
                    course.setDetail(jsonObject.getString("course_detail"));

                    courses.add(course);
                }

                courseListview.setAdapter(courseAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "" + error.toString(), Toast.LENGTH_SHORT).show());

        requestQueue.add(stringRequest);
        courseListview.setOnItemClickListener((parent, view, position, id) -> {

            TextView course_id = view.findViewById(R.id.course_id);
            TextView course_name = view.findViewById(R.id.course_name);
            TextView course_detail = view.findViewById(R.id.course_detail);

            String courseID = course_id.getText().toString();
            String courseNAME = course_name.getText().toString();
            String courseDETAIL = course_detail.getText().toString();


            Intent intent = new Intent(CourseActivity.this, SubjectActivity.class);
            intent.putExtra("course_id", courseID);
            intent.putExtra("course_name", courseNAME);
            intent.putExtra("course_detail", courseDETAIL);
            startActivity(intent);

        });

        nav_view.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.nav_home:

                    Intent intent = new Intent(CourseActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                case R.id.nav_profile:

                    Intent intent1 = new Intent(CourseActivity.this, ProfileActivity.class);
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
                        Intent logOut = new Intent(CourseActivity.this, LoginActivity.class);
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