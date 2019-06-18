package com.example.sanbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton CalenderBtn = findViewById(R.id.CalenderBtn);
        CalenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent mCalendar = new Intent(MainActivity.this, Calendar.class);
               startActivity(mCalendar);
            }
        });

        ImageButton NewsBtn = findViewById(R.id.NewsBtn);
        NewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mNewsActivity = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(mNewsActivity);
            }
        });
    }
}
