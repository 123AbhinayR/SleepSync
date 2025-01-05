package com.example.sleepsync;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    Button buttonStartSleep, buttonEndSleep, buttonViewData, buttonResources;
    TextView textViewSleepDuration, textViewAverageSleep, textViewSleepScore;
    private long startTime, endTime;
    private ArrayList<String> sleepDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        buttonStartSleep = findViewById(R.id.startSleepButton);
        buttonEndSleep = findViewById(R.id.endSleepButton);
        buttonViewData = findViewById(R.id.dataButton);
        buttonResources = findViewById(R.id.resourcesButton);
        textViewSleepDuration = findViewById(R.id.textViewSleepDuration);
        textViewAverageSleep = findViewById(R.id.textView4);
        textViewSleepScore = findViewById(R.id.textView5);

        calendarView.setDate(System.currentTimeMillis());

        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            Toast.makeText(MainActivity.this, day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
        });

        buttonViewData.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AdvancedData.class);
            intent.putStringArrayListExtra("sleepData", sleepDataList);
            startActivity(intent);
        });

        buttonResources.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SleepResourcesPageActivity.class);
            startActivity(intent);
        });

        buttonStartSleep.setOnClickListener(v -> {
            startTime = System.currentTimeMillis();
            textViewSleepDuration.setText("Sleep started...");
            buttonEndSleep.setEnabled(true);
        });

        buttonEndSleep.setOnClickListener(v -> {
            if (startTime == 0) {
                Toast.makeText(MainActivity.this, "Please start sleep first", Toast.LENGTH_SHORT).show();
                return;
            }

            endTime = System.currentTimeMillis();

            long duration = endTime - startTime;
            int hours = (int) (duration / (1000 * 60 * 60));
            int minutes = (int) (duration / (1000 * 60)) % 60;

            String durationText = "Sleep Duration: " + hours + " hours " + minutes + " minutes";
            textViewSleepDuration.setText(durationText);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String sleepEntry = "Start: " + dateFormat.format(new Date(startTime)) +
                    ", End: " + dateFormat.format(new Date(endTime)) +
                    ", Duration: " + hours + "h " + minutes + "m";
            sleepDataList.add(sleepEntry);

            SleepDatabaseHelper dbHelper = new SleepDatabaseHelper(MainActivity.this);
            dbHelper.insertSleepData(dateFormat.format(new Date(startTime)),
                    dateFormat.format(new Date(endTime)), hours, minutes);

            updateAverageSleep();
        });
    }

    private void updateAverageSleep() {
        if (sleepDataList.isEmpty()) {
            textViewAverageSleep.setText("Average Sleep Time: 0 hours 0 minutes");
            textViewSleepScore.setText("Sleep Score: 0");
            return;
        }

        long totalDurationMinutes = 0;

        for (String sleepEntry : sleepDataList) {
            try {
                // Parse the duration part of the entry
                String[] parts = sleepEntry.split(", ");
                if (parts.length >= 3) {
                    String durationPart = parts[2];
                    durationPart = durationPart.replace("Duration: ", "").trim();
                    String[] durationSplit = durationPart.split(" ");

                    int hours = 0, minutes = 0;

                    if (durationSplit.length >= 2) {
                        hours = Integer.parseInt(durationSplit[0].replace("h", "").trim());
                        minutes = Integer.parseInt(durationSplit[1].replace("m", "").trim());
                    }

                    totalDurationMinutes += (hours * 60) + minutes;
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log
            }
        }

        long averageDurationMinutes = totalDurationMinutes / sleepDataList.size();
        int averageHours = (int) (averageDurationMinutes / 60);
        int averageMinutes = (int) (averageDurationMinutes % 60);

        textViewAverageSleep.setText("Average Sleep Time: " + averageHours + " hours " + averageMinutes + " minutes");

        // Calculate sleep score (optional)
        int sleepScore = (int) (averageDurationMinutes * 100) / 720;
        textViewSleepScore.setText("Sleep Score: " + sleepScore);

        if (sleepScore < 50) {
            textViewAverageSleep.setTextColor(getResources().getColor(R.color.red));
            textViewSleepScore.setTextColor(getResources().getColor(R.color.red));
        } else if (sleepScore >= 50 && sleepScore <= 75) {
            textViewAverageSleep.setTextColor(getResources().getColor(R.color.yellow));
            textViewSleepScore.setTextColor(getResources().getColor(R.color.yellow));
        } else {
            textViewAverageSleep.setTextColor(getResources().getColor(R.color.green));
            textViewSleepScore.setTextColor(getResources().getColor(R.color.green));
        }
    }

}
