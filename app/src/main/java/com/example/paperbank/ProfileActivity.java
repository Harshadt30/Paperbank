package com.example.paperbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etGender, etPhone;
    private PaperbankDatabaseHelper db;
    private TextView dashboard;
    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private TextView userMail, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);

        etGender = findViewById(R.id.etGender);
        etPhone = findViewById(R.id.etPhone);
        dashboard = findViewById(R.id.Home);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {

            drawerLayout.openDrawer(GravityCompat.START);
        });

        dashboard.setOnClickListener(v-> { finish(); });

        db = new PaperbankDatabaseHelper(this);
        Cursor cursor = db.userRow();

        if(cursor.moveToNext()){
            View navView = nav_view.getHeaderView(0);
            userMail = navView.findViewById(R.id.userMail);
            username = navView.findViewById(R.id.username);
            userMail.setText(cursor.getString(2));
            username.setText(cursor.getString(1));

            etUsername.setText(cursor.getString(1));
            etEmail.setText(cursor.getString(2));
            etPhone.setText(cursor.getString(3));
            etGender.setText(cursor.getString(4));
        }
        etUsername.clearFocus();

        nav_view.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.nav_home:

                    Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                case R.id.nav_profile:
                    break;

                case R.id.nav_paper :
                    Intent intent2 = new Intent(ProfileActivity.this, CourseActivity.class);
                    startActivity(intent2);
                    finish();
                    break;

                case R.id.nav_email:

                    Toast.makeText(getApplicationContext(), "Visit 192.168.0.111/help/  For more info", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.logOUT:

                    if (db.removeUser()) {
                        Intent logOut = new Intent(ProfileActivity.this, LoginActivity.class);
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