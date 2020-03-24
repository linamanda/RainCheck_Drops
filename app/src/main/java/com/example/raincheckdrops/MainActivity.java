package com.example.raincheckdrops;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.raincheckdrops.ui.dashboard.AlarmActivity;
import com.example.raincheckdrops.ui.dashboard.AlarmReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private TextView alarmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_drops, R.id.navigation_devices)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
        ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void onToggleClicked(View view) {
        Log.d("HELLO","WORLD");
        try {
            if (((ToggleButton) view).isChecked()) {
                Log.d("MyActivity", "Alarm On");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
                Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.cancel(pendingIntent);
                setAlarmText("");
                Log.d("MyActivity", "Alarm Off");
            }
        }catch (Exception e){
            Log.e("ERROR", e.toString());
        }

    }


    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }

}
