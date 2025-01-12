package com.example.sleepsync;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sleepsync.R;

import java.util.ArrayList;

public class AdvancedData extends AppCompatActivity {

    ListView listViewSleepData;
    TextView textViewRecommendations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_data);

        listViewSleepData = findViewById(R.id.listViewSleepData);
        textViewRecommendations = findViewById(R.id.textViewRecommendations);

        ArrayList<String> sleepDataList = getIntent().getStringArrayListExtra("sleepData");

        if (sleepDataList != null && !sleepDataList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sleepDataList);
            listViewSleepData.setAdapter(adapter);
        }

        int sleepScore = calculateSleepScore(sleepDataList); // Calculate the sleep score based on sleep data
        setRecommendations(sleepScore);
    }

    private int calculateSleepScore(ArrayList<String> sleepDataList) {
        if (sleepDataList == null || sleepDataList.isEmpty()) {
            return 0; // Return a default value if no data exists
        }

        long totalDuration = 0;
        for (String sleepEntry : sleepDataList) {
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
                    }
                }
            }
        }

        long averageDurationMinutes = totalDuration / sleepDataList.size();
        int sleepScore = (int) (averageDurationMinutes * 100) / 9; // Example sleep score formula

        return sleepScore;
    }

    private void setRecommendations(int sleepScore) {
        // Declare a variable to hold the recommendation message
        String recommendation;

        // Check the sleep score and assign a recommendation based on the value
        if (sleepScore > 75) {
            // If sleep score is above 75, the recommendation is positive
            recommendation = "• Great job! Keep up the good work!";
        } else if (sleepScore >= 50 && sleepScore <= 75) {
            // If sleep score is between 50 and 75, the recommendation suggests improvements
            recommendation = "• Try to go to bed a little earlier.\n• Consider taking a short nap during the day.";
        } else {
            // If sleep score is below 50, the recommendation is more comprehensive and encourages significant improvement
            recommendation = "• Avoid screens before bed.\n• Avoid caffeine in the evening.\n• Try an earlier bedtime.\n• Improve your sleep routine.";
        }

        // Set the recommendation message to the TextView that displays it
        textViewRecommendations.setText(recommendation);
    }

}
