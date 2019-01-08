package com.example.chokl.multipletimerfinal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TimerRowsAdapter mTimerRowsAdapter;

    private ArrayList<Countdown> countdowns = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        setupFAB();

        setupTimers();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewCountdown(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.deleteAllTimers:
                countdowns.clear();
                Countdown countdown = new Countdown();
                countdowns.add(countdown);
                mTimerRowsAdapter.setNumTimers(1);
                mTimerRowsAdapter.notifyDataSetChanged();
                break;
            case R.id.about:
                View snackBarContainer = findViewById(R.id.activityMain);
                Snackbar.make(snackBarContainer, R.string.about, Snackbar.LENGTH_LONG).show();
                break;
            case R.id.menuCheck:
                toggleMenuItem(item);
                //...
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    private void toggleMenuItem (MenuItem item)
    {
        item.setChecked (!item.isChecked ());
    }

    public void setupTimers()
    {
        Countdown countdown = new Countdown();
        countdowns.add(countdown);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mTimerRowsAdapter = new TimerRowsAdapter(1);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mTimerRowsAdapter);

        timerTimeSet(countdown);//call other countdown updating methods too
    }

    public void createNewCountdown(View view) {
        mTimerRowsAdapter.setNumTimers(mTimerRowsAdapter.getNumTimers() + 1);
        mTimerRowsAdapter.notifyDataSetChanged();
        Countdown countdown = new Countdown();
        countdowns.add(countdown);
        updateCountdown(countdown);
    }

    private void updateCountdown(Countdown countdown) {
        timerNameSet(countdown);
        timerTimeSet(countdown);
        startStopTimer(countdown);
    }

    public void timerNameSet(Countdown countdown) {
        EditText timerLabel = findViewById(R.id.timerLabel);
        countdown.setLabel(timerLabel.getText().toString());
        timerLabel.setFocusable(false);
        timerLabel.setFocusableInTouchMode(true);
    }

    private void timerTimeSet(Countdown countdown) {
        TextView  time = findViewById(R.id.timeString);
        countdown.setRemainingTime(Long.parseLong(time.getText().toString()));
        time.setFocusable(false);
    }

    public void startStopTimer(Countdown countdown) {
        ToggleButton startStopButton = findViewById(R.id.startStopButton);

        if (startStopButton.isChecked()) {
            countdown.runTimer();
        } else if (!startStopButton.isChecked()) {
            countdown.setRunning(false);
        }
    }

    private void resetTimer(Countdown countdown){
       countdown.setRemainingTime(0);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle outstate) {
//
//        super.onSaveInstanceState(outstate);
//        outstate.putInt("NUM_TIMERS", mTimerRowsAdapter.getNumTimers());
//        for (Countdown countdown : mTimerRowsAdapter.getmTimers()) {
//            outstate.putLong("TIME_REMAINING", countdown.getRemainingTime());
//            outstate.putString("LABEL", countdown.getLabel());
//            outstate.putBoolean("RUNNING", countdown.isRunning());
//        }
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mTimerRowsAdapter.setNumTimers(savedInstanceState.getInt("NUM_TIMERS"));
//
//        for (int i = 0; i < mTimerRowsAdapter.getNumTimers(); i++ ){
//
//        }
//
//    }

    private final TimerRowsAdapter.OIClickListener
            listener = new TimerRowsAdapter.OIClickListener ()
    {
        @Override
        public void onToggleClick (int position, View view)
        {
            try {
                startStopTimer(mTimerRowsAdapter.getmTimers()[position]);
            }
            catch (Exception e) {
                Log.d ("STACK", "Toggle Crashed: " + e.getMessage ());
                // No reason for it to crash but since it did...
            }
        }

        @Override
        public void onResetClick(int position, View v) {
            try {
                resetTimer(mTimerRowsAdapter.getmTimers()[position]);
            }
            catch (Exception e) {
                Log.d ("STACK", "Reset Crashed: " + e.getMessage ());
                // No reason for it to crash but since it did...
            }
        }

    };

}
