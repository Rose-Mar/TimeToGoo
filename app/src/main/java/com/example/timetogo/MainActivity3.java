package com.example.timetogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetogo.adapter.CustomAdapter3;
import com.example.timetogo.db.DatabaseHelper;
import com.example.timetogo.db.entity.Activitis;
import com.example.timetogo.db.entity.ListItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {



    ListView listView;
    private DatabaseHelper db;


        private TextView countdownText;

        private CountDownTimer countDownTimer;
        private long timeLeftInMilliseconds;
        long selectedTimeInMillis;
        private long currentTimeMillis;





        Activitis activitis = Activitis.getInstance();

     private int totalTime;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main3);

            db = new DatabaseHelper(this);



            Intent intent = getIntent();
            ArrayList<Integer> idList = intent.getIntegerArrayListExtra("idList");


            List<ListItem> selectedItems = new ArrayList<>();
            for(Integer itemId: idList){
                ListItem listItem = db.getListItem(itemId);
                if(listItem != null) {
                    totalTime += listItem.getTimeActivity();
                    selectedItems.add(listItem);

                    }
            }





            listView = findViewById(R.id.listCountView);
            CustomAdapter3 adapter = new CustomAdapter3(MainActivity3.this, R.layout.ltem_count, selectedItems);
            listView.setAdapter(adapter);




//            activity.getTimeToLeave(selectedTimeInMillis);
            currentTimeMillis = System.currentTimeMillis();


            Intent intentTime = getIntent();
            selectedTimeInMillis = activitis.getTimeToLeave();
//            selectedTimeInMillis = intentTime.getLongExtra("selectedTimeInMillis", 0);


            countdownText = findViewById(R.id.timerTextView);

            startCountdown();

            listView = findViewById(R.id.listCountView);






        }


        private void startCountdown() {


            timeLeftInMilliseconds = selectedTimeInMillis - currentTimeMillis - (totalTime*1000*60);

            Toast.makeText(MainActivity3.this, "totalTime: " + totalTime, Toast.LENGTH_SHORT).show();


            Toast.makeText(MainActivity3.this, "czas: " + timeLeftInMilliseconds, Toast.LENGTH_SHORT).show();

            Toast.makeText(MainActivity3.this, "currentTime: " + currentTimeMillis, Toast.LENGTH_SHORT).show();

            Toast.makeText(MainActivity3.this, "selectedTime: " + selectedTimeInMillis, Toast.LENGTH_SHORT).show();


//            timeLeftInMilliseconds = totalTime*1000*60;

            countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {

                @Override
                public void onTick(long millisecondsUntilFinished) {
                    timeLeftInMilliseconds = millisecondsUntilFinished;
                    updateCountdownText();
                }

                @Override
                public void onFinish() {
                    // Wykonaj odpowiednie działania po zakończeniu odliczania
                    // np. wyświetl powiadomienie lub uruchom inny kod
                }
            }.start();
        }

        private void updateCountdownText() {
            int hours = (int) ((timeLeftInMilliseconds / 1000) / 3600);
            int minutes = (int) ((timeLeftInMilliseconds / 1000) % 3600) / 60;
            int seconds = (int) ((timeLeftInMilliseconds / 1000) % 60);

            String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            countdownText.setText(timeLeftFormatted);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }

}