package com.example.finalproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RingNormalAlarmActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button button_stop;
    private Button button_snooze;


    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_normal_alarm);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        button_stop = findViewById(R.id.button_dismiss);
        button_snooze = findViewById(R.id.button_snooze);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);




        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
                Intent intent = new Intent(RingNormalAlarmActivity.this, NormalAlarmMainActivity.class);
                Toast.makeText(RingNormalAlarmActivity.this, "Alarm off", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


        button_snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();


                Calendar snoozeTime = Calendar.getInstance();
                snoozeTime.add(Calendar.MINUTE, 1);


                setAlarm(snoozeTime.getTimeInMillis());


                Toast.makeText(RingNormalAlarmActivity.this, "Alarm in snooze", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setAlarm(long alarmTime) {
        Intent intent = new Intent(RingNormalAlarmActivity.this, NormalAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(RingNormalAlarmActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent);
    }

    private void stopAlarm() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAlarm();
    }
}