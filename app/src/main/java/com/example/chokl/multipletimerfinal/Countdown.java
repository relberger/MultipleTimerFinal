package com.example.chokl.multipletimerfinal;

import android.os.Handler;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Countdown
{
    @SerializedName ("timeremaining")
    @Expose
    private long remainingTime;

    @SerializedName ("labeltext")
    @Expose
    private String label;

    @SerializedName ("timerrunning")
    @Expose
    private boolean timerRunning;

    private transient Handler mHandler;
    private transient Runnable mRunnable;


    public Countdown ()
    {
        remainingTime = 0;
        setupTimer ();
    }

    private void setupTimer ()
    {
        // Create the Handler object
        mHandler = new Handler ();

        // Create the Runnable that, after being called,
        // calls the on timer tick method and then itself one second later, and on and on...
        mRunnable = new Runnable ()
        {
            @Override
            public void run ()
            {
                onTimerTick ();
                mHandler.postDelayed (this, 1000);
            }
        };
    }

    private void onTimerTick ()
    {
        if (remainingTime > 0) {
            remainingTime--;
        }
        else {
            pauseTimer ();
        }
    }


    private void pauseResumeTimer ()
    {
        if (!timerRunning) {
            resumeTimer ();
        }
        else {
            pauseTimer ();
        }
    }

    private void resumeTimer ()
    {
        timerRunning = true;
        mHandler.postDelayed (mRunnable, 1000);
    }

    private void pauseTimer ()
    {
        mHandler.removeCallbacks (mRunnable);
        timerRunning = false;
    }


    public long getRemainingTime ()
    {
        return remainingTime;
    }

    public void setRemainingTime (long remainingTime)
    {
        this.remainingTime = remainingTime;
    }

    public String getRemainingTimeString ()
    {
        //long hour = 0, min = 0, sec = 0;
        long hour, min, sec;

        hour = (remainingTime / 3_600_000) % 24;
        min = (remainingTime / 60_000) % 60;
        sec = (remainingTime / 1000) % 60;

        Log.d ("NUMBERS", "Remaining Time: " + remainingTime / 1000);
        Log.d ("NUMBERS", "Hours: " + hour);
        Log.d ("NUMBERS", "Minutes: " + min);
        Log.d ("NUMBERS", "Seconds: " + sec);
        
        return String.format (Locale.getDefault (), "%02d:%02d:%02d", hour, min, sec);
    }

    public String getLabel ()
    {
        return label;
    }

    public void setLabel (String label)
    {
        this.label = label;
    }

    public boolean isTimerRunning ()
    {
        return timerRunning;
    }

    public void setTimerRunning (boolean timerRunning)
    {
        this.timerRunning = timerRunning;
        if (timerRunning) {
            resumeTimer ();
        }
        else {
            pauseTimer ();
        }
    }

    public void addHours (long hours)
    {
        remainingTime = remainingTime + (hours * 3_600_000);
    }

    public void addMinutes (long minutes)
    {
        remainingTime = remainingTime + (minutes * 60_000);
    }

    public void addSeconds (long seconds)
    {
        remainingTime = remainingTime + (seconds * 1_000);
    }
}
