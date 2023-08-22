package com.example.finalproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.alarm.AlarmActivity;

public class MainActivity extends AppCompatActivity {


    private Button alarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmButton = findViewById(R.id.button_set_alarm);
        alarmButton.setTypeface(alarmButton.getTypeface(), Typeface.BOLD);
        alarmButton.setAllCaps(false); // Disable all-caps if necessary
        alarmButton.setGravity(Gravity.CENTER);

        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm();
            }
        });
    }

    private void startAlarm() {
        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
        startActivity(intent);
    }
}