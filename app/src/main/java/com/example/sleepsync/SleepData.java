package com.example.sleepsync;

public class SleepData {
    private int id;
    private String startTime;
    private String endTime;
    private int durationHours;
    private int durationMinutes;

    public SleepData(int id, String startTime, String endTime, int durationHours, int durationMinutes) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;
    }

    public int getId() {
        return id;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}
