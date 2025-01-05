package com.example.sleepsync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SleepDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sleep_data.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    public static final String TABLE_NAME = "sleep_data";

    // Column Names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";
    public static final String COLUMN_DURATION_HOURS = "duration_hours";
    public static final String COLUMN_DURATION_MINUTES = "duration_minutes";

    public SleepDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_START_TIME + " TEXT, " +
                COLUMN_END_TIME + " TEXT, " +
                COLUMN_DURATION_HOURS + " INTEGER, " +
                COLUMN_DURATION_MINUTES + " INTEGER);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if exists, and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to insert sleep data into the database
    public void insertSleepData(String startTime, String endTime, int durationHours, int durationMinutes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_START_TIME, startTime);
        contentValues.put(COLUMN_END_TIME, endTime);
        contentValues.put(COLUMN_DURATION_HOURS, durationHours);
        contentValues.put(COLUMN_DURATION_MINUTES, durationMinutes);

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    // Method to retrieve all sleep data
    public Cursor getAllSleepData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}
