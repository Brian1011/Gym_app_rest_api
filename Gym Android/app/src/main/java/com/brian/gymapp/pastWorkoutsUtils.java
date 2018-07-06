package com.brian.gymapp;

public class pastWorkoutsUtils {
    private String workoutDate;
    private String workoutLocation;
    private String workoutType;
    private int workoutReps;

    /*
    public pastWorkoutsUtils(String workoutDate, String workoutLocation, String workoutType, int workoutReps) {
        this.workoutDate = workoutDate;
        this.workoutLocation = workoutLocation;
        this.workoutType = workoutType;
        this.workoutReps = workoutReps;
    }*/

    public String getWorkoutDate() {
        return workoutDate;
    }

    public String getWorkoutLocation() {
        return workoutLocation;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public int getWorkoutReps() {
        return workoutReps;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }

    public void setWorkoutLocation(String workoutLocation) {
        this.workoutLocation = workoutLocation;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public void setWorkoutReps(int workoutReps) {
        this.workoutReps = workoutReps;
    }
}
