package com.example.chokl.multipletimerfinal;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Countdown
{
    private long remainingTime;
    private Timer timer;
    private String label;
    private boolean running;
    private String remainingTimeString;

    Countdown()
    {
        this.timer = new Timer();
    }

    void runTimer()
    {
        TimerTask decrement = new TimerTask()
        {
            @Override
            public void run()
            {
                if (remainingTime >= 0)
                {
                    //clock.setText(getRemainingTimeString());
                    remainingTime = remainingTime - 1000;
                    timeToString();
                }
                else
                {
                    timer.cancel();
                }
            }
        };
        timer.schedule(decrement, 50, 1000);
    }

    private void timeToString()
    {
        long hour = remainingTime / 3_600_000;
        long min = remainingTime % hour;
        long sec = remainingTime % min / 1000;
        remainingTimeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, min, sec);
    }

    public String getRemainingTimeString()
    {
        return remainingTimeString;
    }

    public void setRemainingTimeString(String remainingTimeString)
    {
        this.remainingTimeString = remainingTimeString;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public boolean isRunning()
    {
        return running;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }
}
