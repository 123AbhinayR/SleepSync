package com.example.sleepsync;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sleepsync.R;

import java.util.ArrayList;

public class AdvancedData extends AppCompatActivity {

    ListView listViewSleepData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_data);

        listViewSleepData = findViewById(R.id.listViewSleepData);

        // Get the sleep data list from the Intent
        ArrayList<String> sleepDataList = getIntent().getStringArrayListExtra("sleepData");

        // Check if sleepDataList is not null and display it in the ListView
        if (sleepDataList != null && !sleepDataList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sleepDataList);
            listViewSleepData.setAdapter(adapter);
        }
    }
}