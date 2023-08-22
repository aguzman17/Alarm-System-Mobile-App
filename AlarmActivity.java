package com.example.finalproject;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//import com.example.finalproject.alarm.AlarmActivity;

public class AlarmActivity extends AppCompatActivity {

    private TextView timeTextView;
    private Handler handler;
    private Runnable runnable;
    private MediaPlayer mediaPlayer;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        timeTextView = findViewById(R.id.text_time);
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound); // Replace R.raw.alarm_sound with your sound resource
        stopButton = findViewById(R.id.button_stop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSound();
            }
        });

        // Create a handler to update the time every second
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                handler.postDelayed(this, 1000);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(runnable); // Start updating the time
        playSound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); // Stop updating the time
        stopSound();
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        timeTextView.setText(currentTime);
    }


    private void playSound() {
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void stopSound() {
        mediaPlayer.setLooping(false); // Stop looping
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        stopButton.setEnabled(false); // Disable the stop button to prevent multiple clicks
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Release the MediaPlayer resources if not already released
            mediaPlayer = null;
        }
    }
}