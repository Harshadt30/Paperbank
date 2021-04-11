package com.example.paperbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

public class RequestActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private MaterialToolbar toolbar;
    private TextView home;
    private PaperbankDatabaseHelper db;
    private UrlPaperbank url;
    private TextView userMail, username;
    private EditText reqYear, reqPaper;
    private Button btnReq;
    private AlertDialog.Builder builder;
    private int user_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_request);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        home = findViewById(R.id.Home);
        reqPaper = findViewById(R.id.reqPaper);
        reqYear = findViewById(R.id.reqYear);
        btnReq = findViewById(R.id.btnReq);

        url = new UrlPaperbank();
        db = new PaperbankDatabaseHelper(this);
        Cursor cursor = db.userRow();
        if (cursor.moveToNext()) {

            View navView = nav_view.getHeaderView(0);
            user_id = cursor.getInt(0);
            userMail = navView.findViewById(R.id.userMail);
            username = navView.findViewById(R.id.username);
            userMail.setText(cursor.getString(2));
            username.setText(cursor.getString(1));
        }

        home.setOnClickListener(v -> {
            finish();
        });
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        btnReq.setOnClickListener(v -> {

            if (!validatePaper() | !validateYear()) {
                return;
            }

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url.URl + "request.php", response -> {

                if (Boolean.parseBoolean(response)) {

                    builder = new AlertDialog.Builder(RequestActivity.this);
                    builder.setMessage("Request Sent Successfully").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    }).setNegativeButton("Request Another Papers", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            reqPaper.setText(null);
                            reqYear.setText(null);
                            reqPaper.requestFocus();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    builder = new AlertDialog.Builder(RequestActivity.this);
                    builder.setMessage("Something went wrong").setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finishAffinity();
                            System.exit(0);
                        }
                    }).setNegativeButton("Request Another Papers", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            reqPaper.setText(null);
                            reqYear.setText(null);
                            reqPaper.requestFocus();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }, error -> {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("paper_name", reqPaper.getText().toString());
                    param.put("paper_year", reqYear.getText().toString());
                    param.put("user_id", String.valueOf(user_id));
                    return param;
                }
            };
            requestQueue.add(stringRequest);
        });


        nav_view.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.nav_home:

                    Intent intent = new Intent(RequestActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                case R.id.nav_profile:

                    Intent intent1 = new Intent(RequestActivity.this, ProfileActivity.class);
                    startActivity(intent1);
                    finish();
                    break;

                case R.id.nav_search:

                    Intent intent2 = new Intent(RequestActivity.this, SearchActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.nav_request:
                    break;

                case R.id.nav_paper:
                    Intent intent3 = new Intent(RequestActivity.this, CourseActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.nav_email:

                    Toast.makeText(getApplicationContext(), "Visit 192.168.0.111/help/  For more info", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.logOUT:

                    if (db.removeUser()) {
                        Intent logOut = new Intent(RequestActivity.this, LoginActivity.class);
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


    private boolean validateYear() {

        String password = reqYear.getText().toString().trim();
        if (password.isEmpty()) {

            reqYear.setError("Enter Year");
            return false;
        } else {

            reqYear.setError(null);
            return true;
        }
    }

    private boolean validatePaper() {

        String password = reqPaper.getText().toString().trim();
        if (password.isEmpty()) {

            reqPaper.setError("Enter Paper Name");
            return false;
        } else {

            reqPaper.setError(null);
            return true;
        }
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