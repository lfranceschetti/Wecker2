package com.example.wecker;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TimePicker;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import java.util.Calendar;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hour,minute, true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        Calendar currentTime = Calendar.getInstance();
        Calendar cAlarm = Calendar.getInstance();
        cAlarm.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cAlarm.set(Calendar.MINUTE, minute);
        cAlarm.set(Calendar.SECOND, 0);
        GlobalClass globalClass = (GlobalClass) getActivity().getApplicationContext();
        globalClass.setcAlarm(cAlarm);


        FragmentManager fm = getFragmentManager();
        AlarmFragment fragm = (AlarmFragment) fm.findFragmentByTag("alarm");
        fragm.setSwitchOn();
        fragm.showTimeLeft(cAlarm,currentTime);
        fragm.updateTimeText(cAlarm);
        fragm.startAlarm(cAlarm);
    }










}
