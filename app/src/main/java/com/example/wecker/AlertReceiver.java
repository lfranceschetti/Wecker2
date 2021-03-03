package com.example.wecker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlertReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        GlobalClass globalClass = (GlobalClass) context.getApplicationContext();
        Toast.makeText( context,"RIIING",Toast.LENGTH_LONG).show();
        Boolean switchIsOn = globalClass.getSwitchBoolean();
        if(switchIsOn) {
            Intent ringer = new Intent(context, RingerActivity.class);
            context.startActivity(ringer);
        }else {
            Toast.makeText( context,"No Alarm",Toast.LENGTH_LONG).show();
        }

    }
}
