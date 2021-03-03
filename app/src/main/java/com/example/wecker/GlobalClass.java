package com.example.wecker;

import android.app.Application;

import java.util.Calendar;

public class GlobalClass extends Application{

    private Boolean switchBoolean;
    private Calendar cAlarm;
    private String alarmTime;


    public Boolean getSwitchBoolean() {
        return switchBoolean;
    }

    public void setSwitchBoolean(Boolean switchBoolean) {
        this.switchBoolean = switchBoolean;
    }

    public Calendar getcAlarm() {
        return cAlarm;
    }

    public void setcAlarm(Calendar cAlarm) {
        this.cAlarm = cAlarm;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
}
