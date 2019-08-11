package com.ardakazanci.stopwatchmilliseconds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnPause, btnLap;
    TextView tvTimer;
    Handler customHandler = new Handler();
    LinearLayout container;
    long startTime = 0L, timeInMilliSeconds = 0L, timeSwapBuff = 0L, updateTime = 0L;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {

            timeInMilliSeconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliSeconds;
            int secs = (int) (updateTime / 1000);
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (updateTime % 1000);
            tvTimer.setText("" + mins + ":" + String.format("%02d", secs) + ":"
                    + String.format("%3d", milliseconds));
            customHandler.postDelayed(this, 0);


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btn_start);
        btnLap = findViewById(R.id.btn_lap);
        btnPause = findViewById(R.id.btn_pause);

        tvTimer = findViewById(R.id.tv_timer_value);

        container = findViewById(R.id.container);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startTime = SystemClock.uptimeMillis();

                customHandler.postDelayed(updateTimerThread, 0);

            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeSwapBuff += timeInMilliSeconds;
                customHandler.removeCallbacks(updateTimerThread);

            }
        });

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = layoutInflater.inflate(R.layout.row, null);
                TextView txtValue = addView.findViewById(R.id.tv_content);
                txtValue.setText(tvTimer.getText());
                container.addView(addView);



            }
        });

    }
}
