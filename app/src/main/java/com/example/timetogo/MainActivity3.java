package com.example.timetogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

        private TextView countdownText;

        private CountDownTimer countDownTimer;
        private long timeLeftInMilliseconds;

    int totalTime;











        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main3);


            Intent intent = getIntent();
            totalTime = intent.getIntExtra("totalTime", 0);


            countdownText = findViewById(R.id.timerTextView);

            startCountdown();
        }

        private void startCountdown() {
            timeLeftInMilliseconds = totalTime*1000*60;

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