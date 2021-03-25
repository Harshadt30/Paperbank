package com.example.paperbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {

            drawerLayout.openDrawer(GravityCompat.START);
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();

        nav_view.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.nav_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).addToBackStack(null).commit();
                    break;

                case R.id.nav_paper:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new PaperFragment()).addToBackStack(null).commit();
                    break;

                case R.id.nav_books:

                case R.id.nav_email:

                    break;

                case R.id.nav_profile:

                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
                    break;

                case R.id.logOut:

                    Intent logOut = new Intent(DashboardActivity.this, LoginActivity.class);
                    startActivity(logOut);
                    break;
            }

            return true;
        });
    }

/*
* If User Clicks on Back Button while Navigation Drawer is open
* this function will hide navigation drawer
*/

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }
}