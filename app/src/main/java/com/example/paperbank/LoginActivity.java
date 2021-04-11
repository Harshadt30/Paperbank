package com.example.paperbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogIn;
    private UrlPaperbank urlPaperbank;
    private AlertDialog.Builder builder;
    private PaperbankDatabaseHelper db;
    private int id;
    private String username;
    private String gender;
    private String email;
    private int phone_ne;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogIn = findViewById(R.id.btnLogIn);

        db = new PaperbankDatabaseHelper(this);
        urlPaperbank = new UrlPaperbank();

        btnLogIn.setOnClickListener(v -> {

            if (!validateEmail() | !validatePassword()) {
                return;
            }
            builder = new AlertDialog.Builder(this);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPaperbank.URl + "login.php", response -> {

                if (Boolean.parseBoolean(response)) {

                    addUser();
                } else {

                    builder.setMessage("Wrong Email or Password").setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            etEmail.setText(null);
                            etPassword.setText(null);
                            etEmail.requestFocus();
                        }
                    }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finishAffinity();
                            System.exit(0);
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }, error -> {

                Toast.makeText(getApplicationContext(), "" + error.toString(), Toast.LENGTH_SHORT).show();

            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_email", etEmail.getText().toString());
                    params.put("user_pass", etPassword.getText().toString());
                    return params;
                }
            };

            requestQueue.add(stringRequest);

        });
    }

    private boolean validateEmail() {
        String email = etEmail.getText().toString().trim();

        if (email.isEmpty()) {

            etEmail.setError("Field Can't ba empty");
            return false;
        } else {

            etEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {

        String password = etPassword.getText().toString().trim();
        if (password.isEmpty()) {

            etPassword.setError("Field Can't ba empty");
            return false;
        } else {

            etPassword.setError(null);
            return true;
        }

    }

    private void addUser() {

        RequestQueue requestQueue1 = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, urlPaperbank.URl + "user.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i <= 0; i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        id = jsonObject.getInt("user_id");
                        username = jsonObject.getString("username");
                        gender = jsonObject.getString("user_gender");
                        email = jsonObject.getString("user_email");
                        phone_ne = jsonObject.getInt("user_phone");
                        password = jsonObject.getString("user_password");

                        if (db.addUser(id,username, email, phone_ne, gender, password) == true) {

                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                param.put("email", etEmail.getText().toString());
                param.put("password", etPassword.getText().toString());
                return param;
            }
        };

        requestQueue1.add(stringRequest1);

    }
}