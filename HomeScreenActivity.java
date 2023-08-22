package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreenActivity extends AppCompatActivity {
    private TextView welcomeTextView;
    private TextView menuPromptTextView;
    private Button locationButton;
    private Button alarmButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        welcomeTextView = findViewById(R.id.welcome_text_view);
        menuPromptTextView = findViewById(R.id.menu_prompt_text_view);
        locationButton = findViewById(R.id.location_button);
        alarmButton = findViewById(R.id.alarm_button);
        logoutButton = findViewById(R.id.logout_button);

        // Get the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // User is signed in, display welcome message
            String welcomeMessage = "Welcome, " + user.getEmail();
            welcomeTextView.setText(welcomeMessage);
        } else {
            // User is not signed in, redirect to sign in page
            redirectToSignInPage();
        }

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle location/map button click
                Intent intent = new Intent(HomeScreenActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle set alarm button click
                Intent intent = new Intent(HomeScreenActivity.this, NormalAlarmMainActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); // Sign out the current user

                // Redirect to the sign-in page
                redirectToSignInPage();
            }
        });
    }

    private void redirectToSignInPage() {
        Intent intent = new Intent(HomeScreenActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}