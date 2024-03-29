package com.example.timetogo.db.entity;

import android.os.CountDownTimer;

public class ListItem {

    public static final String TABLE_NAME = "activities";
    public static final String COLUMN_ID = "activity_id";
    public static final String COLUMN_NAME = "activity_name";
    public static final String COLUMN_TIME = "activity_time";

    private String nameActivity;
    private int timeActivity;
    private boolean check;
    private int id;
    private CountDownTimer countDownTimer;

    public boolean isButtonVisible() {
        return buttonVisible;
    }

    public void setButtonVisible(boolean buttonVisible) {
        this.buttonVisible = buttonVisible;
    }

    private boolean buttonVisible;

    public ListItem() {
    }

    public ListItem(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public ListItem(String nameActivity, int timeActivity, int id) {
        this.nameActivity = nameActivity;
        this.timeActivity = timeActivity;
        this.id = id;
        this.buttonVisible = true;
    }

    public ListItem(String nameActivity, int timeActivity, int id, boolean check) {
        this.nameActivity = nameActivity;
        this.timeActivity = timeActivity;
        this.id = id;
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameActivity() {
        return nameActivity;
    }

    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public int getTimeActivity() {
        return timeActivity;
    }

    public void setTimeActivity(int timeActivity) {
        this.timeActivity = timeActivity;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
    }

    public void setCountDownTimer(CountDownTimer countDownTimer) {
        this.countDownTimer = countDownTimer;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
}
