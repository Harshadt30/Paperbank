package com.example.paperbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etGender, etPhone, etPassword;
    private Button btnSingUp;
    private AutoCompleteTextView autoCompleteTextView;
    private UrlPaperbank urlPaperbank;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etGender = findViewById(R.id.etGender);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnSingUp = findViewById(R.id.btnSingUp);
        autoCompleteTextView = findViewById(R.id.etGender);
        String[] genders = {"Male", "Female"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders);
        autoCompleteTextView.setAdapter(arrayAdapter);

        urlPaperbank = new UrlPaperbank();

        btnSingUp.setOnClickListener(v -> {

            if (!validateUsername() | !validateEmail() | !validatePhone() | !validatePassword() | !validateGender()) {

                return;
            }

            builder = new AlertDialog.Builder(this);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPaperbank.URl + "register.php", response -> {

                if (Boolean.parseBoolean(response)) {

                    builder.setMessage("Registered Successfully").setPositiveButton("Log in", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            PaperbankDatabaseHelper db = new PaperbankDatabaseHelper(SignupActivity.this);


                            if (db.addUser(0,etUsername.getText().toString(), etEmail.getText().toString(), Integer.parseInt(etPhone.getText().toString()), etGender.getText().toString(), etPassword.getText().toString()) == true) {

                                Intent intent = new Intent(SignupActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();

                            } else {

                                falseBtn();
                            }
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                } else {

                    falseBtn();
                }
            }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show()) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", etUsername.getText().toString());
                    params.put("user_email", etEmail.getText().toString());
                    params.put("gender", etGender.getText().toString());
                    params.put("user_no", etPhone.getText().toString());
                    params.put("user_pass", etPassword.getText().toString());
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        });
    }

    private boolean validateUsername() {

        String username = etUsername.getText().toString().trim();
        if (username.isEmpty()) {

            etUsername.setError("Enter Username");
            return false;
        } else {

            etUsername.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {

        String email = etEmail.getText().toString().trim();
        if (email.isEmpty()) {

            etEmail.setError("Enter Email address");
            return false;
        } else {

            etEmail.setError(null);
            return true;
        }
    }

    private boolean validateGender() {

        String gender = etGender.getText().toString().trim();
        if (gender.isEmpty()) {

            etGender.setError("Select Gender");
            return false;
        } else {

            etGender.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {

        String phone = etPhone.getText().toString().trim();
        if (phone.isEmpty()) {

            etPhone.setError("Enter Contact no.");
            return false;
        } else {

            etPhone.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {

        String password = etPassword.getText().toString().trim();
        if (password.isEmpty()) {

            etPassword.setError("Enter Password");
            return false;
        } else {

            etPassword.setError(null);
            return true;
        }
    }

    public void falseBtn() {

        builder.setMessage("Registion Failed").setPositiveButton("Try again", (dialog, which) -> {

            etUsername.setText(null);
            etPhone.setText(null);
            etPassword.setText(null);
            etGender.setText(null);
            etEmail.setText(null);

        }).setNegativeButton("Exit", (dialog, which) -> {

            finishAffinity();
            System.exit(0);
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}