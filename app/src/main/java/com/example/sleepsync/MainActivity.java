package com.example.sleepsync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    Calendar calendar;
    Button buttonStartSleep, buttonEndSleep;
    TextView textViewSleepDuration, textViewAverageSleep, textViewSleepScore;

    private long startTime, endTime;
    private ArrayList<String> sleepDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();
        buttonStartSleep = findViewById(R.id.startSleepButton);
        buttonEndSleep = findViewById(R.id.endSleepButton);
        textViewSleepDuration = findViewById(R.id.textViewSleepDuration);
        textViewAverageSleep = findViewById(R.id.textView4); // TextView for Average Sleep
        textViewSleepScore = findViewById(R.id.textView5); // TextView for Sleep Score

        // Initialize calendar
        setDate(4, 1, 2025);
        getDate();

        // Calendar selection listener
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                // Display selected date (corrected month indexing)
                Toast.makeText(MainActivity.this, day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
            }
        });

        // Setting up other buttons

        Button button2 = findViewById(R.id.dataButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdvancedData.class);
                intent.putStringArrayListExtra("sleepData", sleepDataList); // Pass the sleep data list
                startActivity(intent);
            }
        });

        Button button3 = findViewById(R.id.resourcesButton);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SleepResourcesPageActivity.class);
                startActivity(intent);
            }
        });

        // Sleep tracker button logic
        buttonStartSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis(); // Start timer
                textViewSleepDuration.setText("Sleep started...");
                buttonEndSleep.setEnabled(true); // Enable "End Sleep" button
            }
        });

        buttonEndSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startTime == 0) {
                    // If startTime is 0, show an error or return
                    Toast.makeText(MainActivity.this, "Please start sleep first", Toast.LENGTH_SHORT).show();
                    return;
                }

                endTime = System.currentTimeMillis(); // End timer

                // Calculate duration
                long duration = endTime - startTime;
                int hours = (int) (duration / (1000 * 60 * 60));
                int minutes = (int) (duration / (1000 * 60)) % 60;

                // Display duration
                String durationText = "Sleep Duration: " + hours + " hours " + minutes + " minutes";
                textViewSleepDuration.setText(durationText);

                // Store sleep data entry
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String sleepEntry = "Start: " + dateFormat.format(new Date(startTime)) +
                        ", End: " + dateFormat.format(new Date(endTime)) +
                        ", Duration: " + hours + "h " + minutes + "m";
                sleepDataList.add(sleepEntry);

                // (Optional) Log entries
                for (String entry : sleepDataList) {
                    System.out.println(entry);
                }

                // Insert the data into the database
                SleepDatabaseHelper dbHelper = new SleepDatabaseHelper(MainActivity.this);
                dbHelper.insertSleepData(dateFormat.format(new Date(startTime)),
                        dateFormat.format(new Date(endTime)), hours, minutes);

                // Update the average sleep time after adding the new sleep data
                updateAverageSleep();
            }
        });
    }

    public void getDate() {
        long date = calendarView.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        calendar.setTimeInMillis(date);
        String selected_date = simpleDateFormat.format(calendar.getTime());
        Toast.makeText(this, selected_date, Toast.LENGTH_SHORT).show();
    }

    public void setDate(int day, int month, int year) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);  // Adjusted for 0-based month indexing
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milli = calendar.getTimeInMillis();
        calendarView.setDate(milli);
    }

    private void updateAverageSleep() {
        if (sleepDataList.isEmpty()) {
            return; // No data, don't update the average
        }

        long totalDuration = 0;
        for (String sleepEntry : sleepDataList) {
            // Assuming sleepEntry is in the format: "Start: HH:mm, End: HH:mm, Duration: X hours Y minutes"
            String[] parts = sleepEntry.split(", ");
            if (parts.length >= 3) {
                String durationPart = parts[2]; // "Duration: X hours Y minutes"
                String[] durationSplit = durationPart.replace("Duration: ", "").split(" ");
                if (durationSplit.length >= 4) {
                    try {
                        int hours = Integer.parseInt(durationSplit[0]);  // Get hours
                        int minutes = Integer.parseInt(durationSplit[2]); // Get minutes
                        totalDuration += (hours * 60) + minutes; // Convert to total minutes
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        }

        // Calculate the average in minutes
        long averageDurationMinutes = totalDuration / sleepDataList.size();
        int averageHours = (int) (averageDurationMinutes / 60);
        int averageMinutes = (int) (averageDurationMinutes % 60);

        // Calculate the sleep score
        int sleepScore = (int) (averageDurationMinutes * 100) / 9; // Sleep score formula

        // Update the TextView with the calculated average
        String averageText = "Average Sleep Time: " + averageHours + " hours " + averageMinutes + " minutes";
        textViewAverageSleep.setText(averageText);
        textViewSleepScore.setText("Sleep Score: " + sleepScore);

        // Set text colors based on sleep score
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


