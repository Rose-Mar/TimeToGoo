package com.example.timetogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.timetogo.db.entity.Activitis;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Activitis activitis;
    private TimePicker timePicker;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activitis = Activitis.getInstance();
        timePicker = findViewById(R.id.timePicker);
        nextButton = findViewById(R.id.nextBtn);
        timePicker.setIs24HourView(true);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                long selectedTimeInMillis = calendar.getTimeInMillis();
                Toast.makeText(MainActivity.this, "currentTime: " + selectedTimeInMillis, Toast.LENGTH_SHORT).show();

                activitis.setTimeToLeave(selectedTimeInMillis);

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}