package com.example.ramansehmbi.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    Button goButton;

    public void buttonClicked(View view) {

        goButton = (Button) findViewById(R.id.goButton);

        if (counterIsActive) {
            timerTextView.setText("0:30");
            timerSeekBar.setProgress(30);
            timerSeekBar.setEnabled(true);
            countDownTimer.cancel();
            goButton.setText("Go");
            counterIsActive = false;

        } else {
            goButton.setText("Stop!");
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:30");
                    timerSeekBar.setProgress(30);
                    timerSeekBar.setEnabled(true);
                    countDownTimer.cancel();
                    goButton.setText("Go");
                    counterIsActive = false;
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                    mp.start();
                    Toast.makeText(getApplicationContext(), "Time is Up !!", Toast.LENGTH_SHORT).show();
                }
            }.start();

            Log.i("INFO ", "BUTTON IS PRESSED ");
        }
    }

    public void updateTimer(int progress) {
        int minutes = progress / 60;
        int seconds = progress - (minutes * 60);
        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }
        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.seekBar);
        timerTextView = findViewById(R.id.timerTextView);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
