package com.example.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "GPS_SETTINGS";
    private static final String KEY_GPS_ENABLED = "gps_enabled";
    private static final String KEY_UPDATE_INTERVAL = "update_interval";
    private static final String KEY_LOCATION_TRACKING = "location_tracking";
    private static final String KEY_DISTANCE_UNITS = "distance_units";

    private Spinner spinnerDistanceUnits;
    private Switch switchGps;
    private EditText editTextUpdateInterval;
    private Switch switchLocationTracking;
    private Button buttonSave;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        spinnerDistanceUnits = findViewById(R.id.spinner_distance_units);

        switchGps = findViewById(R.id.switch_gps);
        editTextUpdateInterval = findViewById(R.id.editText_update_interval);
        switchLocationTracking = findViewById(R.id.switch_location_tracking);
        buttonSave = findViewById(R.id.button_save);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        boolean gpsEnabled = sharedPreferences.getBoolean(KEY_GPS_ENABLED, false);
        int updateInterval = sharedPreferences.getInt(KEY_UPDATE_INTERVAL, 5000);
        boolean locationTrackingEnabled = sharedPreferences.getBoolean(KEY_LOCATION_TRACKING, false);
        String distanceUnits = sharedPreferences.getString(KEY_DISTANCE_UNITS, "Kilometers");

        switchGps.setChecked(gpsEnabled);
        editTextUpdateInterval.setText(String.valueOf(updateInterval));
        switchLocationTracking.setChecked(locationTrackingEnabled);

        int selectedIndex = getIndexFromDistanceUnits(distanceUnits);
        spinnerDistanceUnits.setSelection(selectedIndex);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }

    private int getIndexFromDistanceUnits(String distanceUnits) {
        String[] distanceUnitsArray = getResources().getStringArray(R.array.distance_units_array);
        for (int i = 0; i < distanceUnitsArray.length; i++) {
            if (distanceUnitsArray[i].equals(distanceUnits)) {
                return i;
            }
        }
        return 0; // Default to the first item if not found
    }

    private void saveSettings() {
        boolean gpsEnabled = switchGps.isChecked();
        int updateInterval = Integer.parseInt(editTextUpdateInterval.getText().toString());
        boolean locationTrackingEnabled = switchLocationTracking.isChecked();
        String distanceUnits = spinnerDistanceUnits.getSelectedItem().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_GPS_ENABLED, gpsEnabled);
        editor.putInt(KEY_UPDATE_INTERVAL, updateInterval);
        editor.putBoolean(KEY_LOCATION_TRACKING, locationTrackingEnabled);
        editor.putString(KEY_DISTANCE_UNITS, distanceUnits);
        editor.apply();
    }
}