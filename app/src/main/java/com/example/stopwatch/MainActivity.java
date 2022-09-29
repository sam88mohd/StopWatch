package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private int seconds = 0;
    private boolean running, wasRunning;
    private TextView clockView;
    private Button start, stop, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        initView();
        runTimer();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = true;
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStop();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
                seconds = 0;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(wasRunning){
            running = true;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    private void initView(){
        Log.d(TAG,"initView: Started");

        clockView = findViewById(R.id.clockView);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        reset = findViewById(R.id.reset);
    }

    private void runTimer(){
        Log.d(TAG, "runTimer: Started");
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                String time = String.format(Locale.getDefault(),
                        "%02d:%02d:%02d", hours, minutes,secs);

                clockView.setText(time);

                if(running){
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }
}