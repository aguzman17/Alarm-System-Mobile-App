package com.example.finalproject;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class NormalAlarmDetailsActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 123;

    private TimePicker pick_time;
    private Button button_save;
    private Button button_cancel;

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_alarm_details);

        pick_time = findViewById(R.id.time_picker);
        button_save = findViewById(R.id.button_save);
        button_cancel = findViewById(R.id.button_cancel);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        sharedPreferences = getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE);


        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveAlarm();
            }
        });


        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void saveAlarm() {
        int hour = pick_time.getCurrentHour();
        int minute = pick_time.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        long alarmTime = calendar.getTimeInMillis();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("AlarmTime", alarmTime);
        editor.apply();

        if (checkPermission()) {
            setAlarm(alarmTime);
            Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }

        else {
            requestPermission();
        }
    }

    private void setAlarm(long alarmTime) {
        Intent intent = new Intent(NormalAlarmDetailsActivity.this, NormalAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(NormalAlarmDetailsActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.SET_ALARM);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SET_ALARM}, PERMISSION_REQUEST_CODE);
    }
}