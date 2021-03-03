package com.example.wecker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;


public class AlarmFragment extends Fragment {

    TextView text;
    Switch alarmOnOFF;

    public void setSwitchOn(){
        alarmOnOFF.setChecked(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container,false);

        final GlobalClass globalClass = (GlobalClass) getActivity().getApplicationContext();
        text = view.findViewById(R.id.time);
        if(globalClass.getcAlarm()!=null) {
            Calendar cAlarm = globalClass.getcAlarm();
            updateTimeText(cAlarm);
        }

        alarmOnOFF = view.findViewById(R.id.alarmOnOff);
        if (globalClass.getSwitchBoolean() != null) {
            alarmOnOFF.setChecked(globalClass.getSwitchBoolean());
        }
        alarmOnOFF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {



            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                globalClass.setSwitchBoolean(isChecked);
                if(isChecked){
                    Calendar cAlarm = globalClass.getcAlarm();
                    Calendar currentTime = Calendar.getInstance();

                    showTimeLeft(cAlarm,currentTime);

                }
            }
        });


        return view;

    }

    public void showTimeLeft(Calendar cAlarm, Calendar currentTime){
        if(cAlarm!=null) {
            int cAlarmMillis = (int) cAlarm.getTimeInMillis();
            int currentTimeMillis = (int) currentTime.getTimeInMillis();
            int milliDifference;
            if (cAlarmMillis >= currentTimeMillis) {
                milliDifference = cAlarmMillis - currentTimeMillis;
            } else {
                milliDifference = cAlarmMillis - currentTimeMillis + 86400000;
            }
            int hours = milliDifference / (60 * 60 * 1000);
            int timeleft = milliDifference % (60 * 60 * 1000);
            int minutes = timeleft / (60 * 1000);
            String message;
            if (hours >= 1) {
                message = "In " + hours + " hours and " + minutes + " minutes";
            } else {
                message = "In " + minutes + " minutes";
            }
            Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    public void updateTimeText(Calendar cAlarm) {
        String timeString = cAlarm.get(Calendar.HOUR_OF_DAY)+":";
        if(cAlarm.get(Calendar.MINUTE)<=9) {
            timeString += "0" + cAlarm.get(Calendar.MINUTE);
        }else{
            timeString += cAlarm.get(Calendar.MINUTE);
        }

        text.setText(timeString);
    }


    public void startAlarm(Calendar cAlarm) {

        Calendar currentTime = Calendar.getInstance();
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            if (cAlarm.getTimeInMillis() >= currentTime.getTimeInMillis()) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cAlarm.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cAlarm.getTimeInMillis() + 86400000, pendingIntent);
            }
        }
    }

}
