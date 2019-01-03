package com.example.chokl.multipletimerfinal;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Countdown {
    private long remainingTime;
    private Timer timer;
    private String label;
    private boolean running;

    Countdown(long time, String label, boolean running) {
        this.timer = new Timer();
        remainingTime = time;
        this.label = label;
        this.running = running;
    }

    void runTimer() {
        TimerTask decrement = new TimerTask() {
            @Override
            public void run() {
                if(remainingTime >= 0){
                    //clock.setText(getRemainingTimeString());
                    remainingTime = remainingTime - 1000;
                }else {
                    timer.cancel();
                }
            }
        };
        timer.schedule(decrement, 50, 1000);
    }

    String getRemainingTimeString() {
        long hour = remainingTime / 3_600_000;
        long min = remainingTime % hour;
        long sec = remainingTime % min / 1000;
        return String.format(Locale.getDefault(),"%02d:%02d:%02d", hour, min, sec);
    }
}
