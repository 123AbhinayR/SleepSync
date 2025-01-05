package com.example.sleepsync;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SleepResourcesPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);  // Make sure you set the correct layout

        ImageView youthImage = findViewById(R.id.youthImage);
        youthImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SleepResourcesPageActivity.this, YouthSleepResources.class);
                startActivity(intent);
            }
        });

        ImageView middleAgeImage = findViewById(R.id.middleAgeImage);
        middleAgeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SleepResourcesPageActivity.this, MiddleAgedSleepResources.class);
                startActivity(intent);
            }
        });

        ImageView oldImage = findViewById(R.id.oldImage);
        oldImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SleepResourcesPageActivity.this, OldSleepResources.class);
                startActivity(intent);
            }
        });
    }
}
