package com.example.sleepsync;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class YouthSleepResources extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youth_sleep_resources);

        ImageView Art1 = findViewById(R.id.YouthSleepArt1);
        Art1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.nationwidechildrens.org/specialties/sleep-disorder-center/sleep-tips-for-adolescents";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Art2 = findViewById(R.id.YouthSleepArt2);
        Art2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://pmc.ncbi.nlm.nih.gov/articles/PMC2528821/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Art3 = findViewById(R.id.YouthSleepArt3);
        Art3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.sleepfoundation.org/teens-and-sleep";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Art4 = findViewById(R.id.YouthSleepArt4);
        Art4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.hopkinsmedicine.org/health/wellness-and-prevention/teenagers-and-sleep-how-much-sleep-is-enough";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Art5 = findViewById(R.id.YouthSleepArt5);
        Art5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://caringforkids.cps.ca/handouts/healthy-living/teens_and_sleep";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Video1 = findViewById(R.id.YouthVideo1);
        Video1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=7mTQbtZnN68&ab_channel=JohnsHopkinsAllChildren%27sHospital";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Video2 = findViewById(R.id.YouthVideo2);
        Video2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=G0Zj_InJ4BQ";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Video3 = findViewById(R.id.YouthVideo3);
        Video3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=jrLBaJDwHyw";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Video4 = findViewById(R.id.YouthVideo4);
        Video4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=54won7qmswI";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Video5 = findViewById(R.id.YouthVideo5);
        Video5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=qoXM4iykEz8";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
