package com.example.finalproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NormalAlarmMainActivity extends AppCompatActivity {

    private TextView curr_time_tv;
    private TextView alarm_status;
    private Button button_set_alarm;
    private Button button_disable_alarm;

    private AlarmManager alarmManager;

    private SharedPreferences sharedPreferences;

    private Handler handler = new Handler();
    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            updateCurrentTime();
            handler.postDelayed(this, 60000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_alarm_activity_main);

        curr_time_tv = findViewById(R.id.text_current_time);
        alarm_status = findViewById(R.id.text_alarm_status);
        button_set_alarm = findViewById(R.id.button_set_alarm);
        button_disable_alarm = findViewById(R.id.button_disable_alarm);
        sharedPreferences = getSharedPreferences("AlarmPrefs", MODE_PRIVATE);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        updateCurrentTime();


        button_set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAlarmDetailsActivity();
            }
        });


        button_disable_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                disableAlarm();
            }
        });
    }


    private void updateCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = dateFormat.format(calendar.getTime());
        curr_time_tv.setText("Current Time: " + currentTime);
    }

    private void openAlarmDetailsActivity() {
        Intent intent = new Intent(NormalAlarmMainActivity.this, NormalAlarmDetailsActivity.class);
        startActivityForResult(intent, 1);
    }

    private void disableAlarm() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("AlarmTime");
        editor.apply();


        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(NormalAlarmMainActivity.this, NormalAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(NormalAlarmMainActivity.this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);


        updateAlarmStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAlarmStatus();
        handler.postDelayed(updateTimeRunnable, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(updateTimeRunnable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            updateAlarmStatus();
            updateCurrentTime();
        }
    }

    private void updateAlarmStatus() {

        long alarmTime = sharedPreferences.getLong("AlarmTime", 0);
        if (alarmTime > 0) {

            long currentTime = Calendar.getInstance().getTimeInMillis();


            if (currentTime < alarmTime) {
                alarm_status.setText("Alarm set");
            }


            else {
                alarm_status.setText("Alarm passed");
            }
        }

        else {
            alarm_status.setText("No alarm set");
        }
    }
}