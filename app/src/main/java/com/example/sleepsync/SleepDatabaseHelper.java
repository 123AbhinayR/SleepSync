package com.example.sleepsync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// This class helps manage the database operations for storing and retrieving sleep data
public class SleepDatabaseHelper extends SQLiteOpenHelper {

    // Database details
    // Private constants (For Encapsulation)
    private static final String DATABASE_NAME = "sleep_data.db";  // Database name
    private static final int DATABASE_VERSION = 1;  // Database version

    private static final String TABLE_NAME = "sleep_data";  // Table name
    private static final String COLUMN_ID = "id";  // Primary key column
    private static final String COLUMN_START_TIME = "start_time";  // Start time column
    private static final String COLUMN_END_TIME = "end_time";  // End time column
    private static final String COLUMN_DURATION_HOURS = "duration_hours";  // Duration in hours
    private static final String COLUMN_DURATION_MINUTES = "duration_minutes";  // Duration in minutes

    //Getter methods for external access
    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    // Constructor for initializing the database helper
    // Context is passed in to interact with the app's database
    public SleepDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // Initialize the SQLiteOpenHelper with the database name and version
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table query for sleep_data table
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  // ID column as primary key
                COLUMN_START_TIME + " TEXT, " +  // Start time column
                COLUMN_END_TIME + " TEXT, " +  // End time column
                COLUMN_DURATION_HOURS + " INTEGER, " +  // Duration in hours
                COLUMN_DURATION_MINUTES + " INTEGER);";  // Duration in minutes
        db.execSQL(createTableQuery);  // Execute the query to create the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and recreate it to upgrade the database version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);  // Recreate the table
    }

    // Method to insert a sleep record into the database
    public void insertSleepData(String startTime, String endTime, int durationHours, int durationMinutes) {
        SQLiteDatabase db = this.getWritableDatabase();  // Get writable database instance
        ContentValues contentValues = new ContentValues();  // To hold the data to be inserted

        // Insert values into the ContentValues object
        contentValues.put(COLUMN_START_TIME, startTime);
        contentValues.put(COLUMN_END_TIME, endTime);
        contentValues.put(COLUMN_DURATION_HOURS, durationHours);
        contentValues.put(COLUMN_DURATION_MINUTES, durationMinutes);

        // Insert the data into the table
        db.insert(TABLE_NAME, null, contentValues);
        db.close();  // Close the database after insertion
    }

    // Method to retrieve all sleep data from the database
    public Cursor getAllSleepData() {
        SQLiteDatabase db = this.getReadableDatabase();  // Get readable database instance
        // Query the database and return the Cursor object for accessing the data
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}
