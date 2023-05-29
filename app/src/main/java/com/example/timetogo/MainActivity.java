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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Activitis activitis = Activitis.getInstance();

        TimePicker timePicker = findViewById(R.id.timePicker);
        Button nextButton = findViewById(R.id.nextBtn);

        //TODO
        // Obsługę tego, że ktoś nie przestawi zegara z pozycji początkowej

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE, minute);

                long selectedTimeInMillis = calendar.getTimeInMillis();
                Toast.makeText(MainActivity.this, "currentTime: " + selectedTimeInMillis, Toast.LENGTH_SHORT).show();


                Intent intentTime = new Intent(MainActivity.this, MainActivity3.class);
                intentTime.putExtra("selectedTimeInMillis", selectedTimeInMillis);

//

                activitis.setTimeToLeave(selectedTimeInMillis);




                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });







    }
}