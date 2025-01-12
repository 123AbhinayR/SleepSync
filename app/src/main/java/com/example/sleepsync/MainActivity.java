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

// This class manages the user interface for displaying sleep data and access to all pages.
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
            // Create a sleep entry as a string containing the start time, end time, and duration
            String sleepEntry = "Start: " + dateFormat.format(new Date(startTime)) + // Format and add the start time
                    ", End: " + dateFormat.format(new Date(endTime)) + // Format and add the end time
                    ", Duration: " + hours + "h " + minutes + "m"; // Join the duration in hours and minutes

            // Add the formatted sleep entry to the list of sleep data
            sleepDataList.add(sleepEntry);


            SleepDatabaseHelper dbHelper = new SleepDatabaseHelper(MainActivity.this);
            dbHelper.insertSleepData(dateFormat.format(new Date(startTime)),
                    dateFormat.format(new Date(endTime)), hours, minutes);

            updateAverageSleep();
        });
    }

    private void updateAverageSleep() {
        // Check if the sleepDataList is empty
        if (sleepDataList.isEmpty()) {
            textViewAverageSleep.setText("Average Sleep Time: 0 hours 0 minutes");
            textViewSleepScore.setText("Sleep Score: 0");
            return; // Exit method if no sleep data
        }

        // Initialize total duration in minutes
        long totalDurationMinutes = 0;

        // Iterate through each sleep entry in the list
        for (String sleepEntry : sleepDataList) {
            try {
                // Split the sleep entry into parts based on commas
                String[] parts = sleepEntry.split(", ");
                if (parts.length >= 3) {
                    // Extract the duration part (assumes it's the third part)
                    String durationPart = parts[2];
                    durationPart = durationPart.replace("Duration: ", "").trim(); // Clean the string
                    String[] durationSplit = durationPart.split(" "); // Split into hours and minutes

                    int hours = 0, minutes = 0;

                    // Parse the hours and minutes
                    if (durationSplit.length >= 2) {
                        hours = Integer.parseInt(durationSplit[0].replace("h", "").trim()); // Hours
                        minutes = Integer.parseInt(durationSplit[1].replace("m", "").trim()); // Minutes
                    }

                    // Add the duration to total minutes (hours * 60 + minutes)
                    totalDurationMinutes += (hours * 60) + minutes;
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log any exceptions (in case of invalid data)
            }
        }

        // Calculate the average sleep duration in minutes
        long averageDurationMinutes = totalDurationMinutes / sleepDataList.size();
        int averageHours = (int) (averageDurationMinutes / 60); // Convert to hours
        int averageMinutes = (int) (averageDurationMinutes % 60); // Remainder as minutes

        // Update the UI with the average sleep time
        textViewAverageSleep.setText("Average Sleep Time: " + averageHours + " hours " + averageMinutes + " minutes");

        // Calculate the sleep score (based on 720 minutes as the maximum score)
        int sleepScore = (int) (averageDurationMinutes * 100) / 720;
        textViewSleepScore.setText("Sleep Score: " + sleepScore);

        // Change the text color based on the sleep score
        if (sleepScore < 50) {
            // Red if score is less than 50
            textViewAverageSleep.setTextColor(getResources().getColor(R.color.red));
            textViewSleepScore.setTextColor(getResources().getColor(R.color.red));
        } else if (sleepScore >= 50 && sleepScore <= 75) {
            // Yellow if score is between 50 and 75
            textViewAverageSleep.setTextColor(getResources().getColor(R.color.yellow));
            textViewSleepScore.setTextColor(getResources().getColor(R.color.yellow));
        } else {
            // Green if score is above 75
            textViewAverageSleep.setTextColor(getResources().getColor(R.color.green));
            textViewSleepScore.setTextColor(getResources().getColor(R.color.green));
        }
    }


}
