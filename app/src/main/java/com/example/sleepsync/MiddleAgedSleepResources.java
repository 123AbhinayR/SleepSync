package com.example.sleepsync;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MiddleAgedSleepResources extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_aged_sleep_resources);

        ImageView Art1 = findViewById(R.id.MiddleAgedSleepArt1);
        Art1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.health.harvard.edu/blog/how-much-sleep-do-you-actually-need-202310302986";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Art2 = findViewById(R.id.MiddleAgedSleepArt2);
        Art2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.mayoclinic.org/healthy-lifestyle/adult-health/in-depth/sleep/art-20048379#:~:text=Stick%20to%20a%20sleep%20schedule,time%20every%20day%2C%20including%20weekends.";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Art3 = findViewById(R.id.MiddleAgedSleepArt3);
        Art3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.headspace.com/sleep/how-to-sleep-better";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Video1 = findViewById(R.id.MiddleAgedVideo1);
        Video1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=t0kACis_dJE";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Video2 = findViewById(R.id.MiddleAgedVideo2);
        Video2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=lRp5AC9W_F8";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ImageView Video3 = findViewById(R.id.MiddleAgedVideo3);
        Video3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=-KVywYrX82w";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
