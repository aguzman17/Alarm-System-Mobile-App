package com.example.finalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NormalAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Trigger the alarm ring screen (RingAlarmActivity) and handle actions
        Intent ringIntent = new Intent(context, RingNormalAlarmActivity.class);
        ringIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(ringIntent);
    }
}