package com.example.scoops;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHandler {

    public Context context;

    public AlarmHandler(Context context){
        this.context = context;

    }


    public void setAlarmManager(){
        Intent intent = new Intent(context, BackgroundRun.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(alarmManager != null){
            long triggerAfter = 1000;
            long triggerEvery = 1000;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 1, sender);

        }
    }

    public void cancelAlarmManager(){
        Intent intent = new Intent(context, BackgroundRun.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(alarmManager != null){
            alarmManager.cancel(sender);
        }
    }
}
