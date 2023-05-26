package com.example.timetogo.db.entity;

public class ListItem {



    //Constants for Database
    public static final String TABLE_NAME = "activities";
    public static final String COLUMN_ID = "activity_id";
    public static final String COLUMN_NAME = "activity_name";
    public static final String COLUMN_TIME = "activity_time";





    private String nameActivity;
    private String timeActivity;
    private boolean check;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public ListItem(){

    }

    public ListItem(String nameActivity, String timeActivity, int id) {
        this.nameActivity = nameActivity;
        this.timeActivity = timeActivity;
        this.id = id;
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





    //SQL Query: Creating the table


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT,"
            + COLUMN_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

}
