package com.example.sanbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button CalenderBtn = findViewById(R.id.CalenderBtn);
        CalenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent mCalendar = new Intent(MainActivity.this, Calendar.class);
               startActivity(mCalendar);
            }
        });

        Button NewsBtn = findViewById(R.id.NewsBtn);
        NewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mNewsActivity = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(mNewsActivity);
            }
        });
    }
}
