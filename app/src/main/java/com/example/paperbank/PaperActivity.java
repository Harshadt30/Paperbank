package com.example.paperbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PaperActivity extends AppCompatActivity {

    com.google.android.material.appbar.MaterialToolbar paperTopBar;
    ListView lstPapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);

        Intent intent = getIntent();

        String[] paperArrayList = {"2020 PHP", "2021 PHP", "2022 PHP", "2023 PHP"};
        paperTopBar = findViewById(R.id.paperTopBar);
        lstPapers = findViewById(R.id.lstPapers);

        PaperAdapter paperAdapter = new PaperAdapter(this, paperArrayList);
        lstPapers.setAdapter(paperAdapter);

        String paperTitle = intent.getStringExtra("Paper");
        paperTopBar.setTitle(paperTitle);
    }
}