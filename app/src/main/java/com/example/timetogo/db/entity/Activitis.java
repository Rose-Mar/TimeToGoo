package com.example.timetogo.db.entity;

public class Activitis {

    private static Activitis instance;
    private long timeToLeave;

    private Activitis() {}

    public static Activitis getInstance() {
        if (instance == null) {
            instance = new Activitis();
        }
        return instance;
    }

    public long getTimeToLeave() {
        return timeToLeave;
    }

    public void setTimeToLeave(long timeToLeave) {
        this.timeToLeave = timeToLeave;
    }
}
