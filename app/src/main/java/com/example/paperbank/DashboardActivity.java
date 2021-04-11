package com.example.paperbank;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private MaterialToolbar toolbar;
    private MaterialCardView profile, papers, search, request;
    private PaperbankDatabaseHelper db;
    private TextView userMail, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // find all elements
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        profile = findViewById(R.id.profile);
        papers = findViewById(R.id.papers);
        search = findViewById(R.id.search);
        request = findViewById(R.id.request);

        db = new PaperbankDatabaseHelper(this);

        Cursor cursor = db.userRow();
        if (cursor.moveToNext()) {

            View navView = nav_view.getHeaderView(0);
            userMail = navView.findViewById(R.id.userMail);
            username = navView.findViewById(R.id.username);
            userMail.setText(cursor.getString(2));
            username.setText(cursor.getString(1));
        }

        // adding actionbar support in activty
        setSupportActionBar(toolbar);

        //open navigation drawer by hamburger on toolbar
        toolbar.setNavigationOnClickListener(v -> {

            drawerLayout.openDrawer(GravityCompat.START);
        });

        //Adding click listner on cardviews

        papers.setOnClickListener(v -> {

            Intent paperIntent = new Intent(DashboardActivity.this, CourseActivity.class);
            startActivity(paperIntent);
        });

        profile.setOnClickListener(v -> {

            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        search.setOnClickListener(v -> {

            Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        request.setOnClickListener(v-> {

            Intent intent = new Intent(DashboardActivity.this, RequestActivity.class);
            startActivity(intent);
        });

        //Handle clicks on drawer
        nav_view.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.nav_home:
                    break;

                case R.id.nav_search:

                    Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;

                case R.id.nav_request :
                    Intent intent5 = new Intent(DashboardActivity.this, RequestActivity.class);
                    startActivity(intent5);
                    break;

                case R.id.nav_profile:

                    Intent intent1 = new Intent(DashboardActivity.this, ProfileActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.nav_paper:

                    Intent intent2 = new Intent(DashboardActivity.this, CourseActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.nav_email:

                    Toast.makeText(getApplicationContext(), "Visit 192.168.0.111/help/  For more info", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.logOUT:

                    if (db.removeUser()) {
                        Intent logOut = new Intent(DashboardActivity.this, LoginActivity.class);
                        startActivity(logOut);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            return true;
        });
    }

    /**
     * If User Clicks on Back Button while Navigation Drawer is open
     * this function will hide navigation drawer
     **/

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }
}