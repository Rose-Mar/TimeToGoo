package com.example.timetogo;

public class ListItem {

    private String nameActivity;
    private String timeActivity;
    private boolean check;

    public ListItem(String nameActivity, String timeActivity, boolean check) {
        this.nameActivity = nameActivity;
        this.timeActivity = timeActivity;
        this.check = check;
    }

    public String getNameActivity() {
        return nameActivity;
    }

    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public String getTimeActivity() {
        return timeActivity;
    }

    public void setTimeActivity(String timeActivity) {
        this.timeActivity = timeActivity;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
