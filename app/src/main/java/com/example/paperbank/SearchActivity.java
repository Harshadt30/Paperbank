package com.example.paperbank;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private EditText etSearch;
    private NavigationView nav_view;
    private MaterialToolbar toolbar;
    private ListView searchListview;
    private TextView home;
    private UrlPaperbank urlPaperbank;
    private ArrayList<Papers> papers;
    private PaperAdapter paperAdapter;
    private DownloadManager downloadManager;
    private PaperbankDatabaseHelper db;
    private TextView userMail, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        searchListview = findViewById(R.id.searchListview);
        nav_view = findViewById(R.id.nav_view);
        home = findViewById(R.id.Home);
        etSearch = findViewById(R.id.etSearch);

        db = new PaperbankDatabaseHelper(this);

        Cursor cursor = db.userRow();
        urlPaperbank = new UrlPaperbank();

        if(cursor.moveToNext()) {

            View navView = nav_view.getHeaderView(0);
            userMail = navView.findViewById(R.id.userMail);
            username = navView.findViewById(R.id.username);
            userMail.setText(cursor.getString(2));
            username.setText(cursor.getString(1));
        }

        home.setOnClickListener(v -> { finish(); });

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        papers = new ArrayList<>();
        paperAdapter = new PaperAdapter(this, papers);

        if(etSearch.getText().toString().equals("")) {

            papers.clear();
            searchListview.setAdapter(null);
            paperAdapter.notifyDataSetChanged();
        }

        Toast.makeText(getApplicationContext(), etSearch.getText().toString(), Toast.LENGTH_SHORT).show();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().length() >= 1) {

                    RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPaperbank.URl + "search.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                papers.clear();
                                paperAdapter.notifyDataSetChanged();
                                searchListview.setAdapter(null);

                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Papers paper = new Papers();

                                    paper.setPaper_year(String.valueOf(jsonObject.getInt("paper_year")));
                                    paper.setPaper_name(jsonObject.getString("paper_name"));
                                    paper.setPaper_path(jsonObject.getString("paper_path"));
                                    paper.setPaper_size(jsonObject.getString("paper_size"));
                                    papers.add(paper);
                                }

                                searchListview.setAdapter(paperAdapter);

                                searchListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        TextView file_name = view.findViewById(R.id.paper_name);
                                        String fileName = file_name.getText().toString();
                                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                                        Uri uri = Uri.parse("http://192.168.0.111/paper_bank/paper_upload/uploaded_paper/" + fileName);
                                        DownloadManager.Request request = new DownloadManager.Request(uri);
                                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                        Long refrence = downloadManager.enqueue(request);
                                    }
                                });
                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(SearchActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<String, String>();
                            String searchVal = s.toString();
                            param.put("search", searchVal);
                            return param;
                        }
                    };

                    requestQueue.add(stringRequest);
                }

            }
        });

        if(etSearch.getText().toString().equals("")) {

            papers.clear();
            searchListview.setAdapter(null);
            paperAdapter.notifyDataSetChanged();
        }

        nav_view.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.nav_home:

                    Intent intent2 = new Intent(SearchActivity.this, DashboardActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.nav_profile:

                    Intent intent1 = new Intent(SearchActivity.this, ProfileActivity.class);
                    startActivity(intent1);
                    finish();
                    break;

                case R.id.nav_paper :

                    Intent intent = new Intent(SearchActivity.this, CourseActivity.class);
                    startActivity(intent);
                    break;

                case R.id.nav_search:
                    break;

                case R.id.nav_request :

                    Intent intent3 = new Intent(SearchActivity.this, RequestActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.nav_email:

                    Toast.makeText(getApplicationContext(), "Visit 192.168.0.111/help/  For more info", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.logOUT:

                    if (db.removeUser()) {
                        Intent logOut = new Intent(SearchActivity.this, LoginActivity.class);
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