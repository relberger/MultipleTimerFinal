package com.example.chokl.multipletimerfinal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private TimerRowsAdapter mTimerRowsAdapter;

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

    public void setupTimers() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mTimerRowsAdapter = new TimerRowsAdapter(mTimerRowsAdapter.getNumTimers());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mTimerRowsAdapter);
    }

    public void createNewCountdown(View view) {
        mTimerRowsAdapter.setNumTimers(mTimerRowsAdapter.getNumTimers() + 1);
        mTimerRowsAdapter.notifyDataSetChanged();
        Countdown countdown = new Countdown();
        updateCountdown(countdown);
    }

    private void updateCountdown(Countdown countdown) {
        timerNameSet(countdown);
        timerTimeSet(countdown);
        startStopTimer(countdown);
    }

    public void timerNameSet(Countdown countdown) {
        EditText timerName = findViewById(R.id.timerLabel);
        countdown.setLabel(timerName.getText().toString());
        timerName.setFocusable(false);
        timerName.setFocusableInTouchMode(true);
    }

    private void timerTimeSet(Countdown countdown) {
    }

    public void startStopTimer(Countdown countdown) {
        ToggleButton startStopButton = findViewById(R.id.startStopButton);

        if (startStopButton.isChecked()) {
            countdown.runTimer();
        } else if (!startStopButton.isChecked()) {
            countdown.setRunning(false);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    protected void onSaveInstanceState(Bundle outstate) {

        super.onSaveInstanceState(outstate);
        outstate.putInt("NUM_TIMERS", mTimerRowsAdapter.getNumTimers());
        for (Countdown countdown : mTimerRowsAdapter.getmTimers()) {
            outstate.putLong("TIME_REMAINING", countdown.getRemainingTime());
            outstate.putString("LABEL", countdown.getLabel());
            outstate.putBoolean("RUNNING", countdown.isRunning());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTimerRowsAdapter.setNumTimers(savedInstanceState.getInt("NUM_TIMERS"));

        for (int i = 0; i < mTimerRowsAdapter.getNumTimers(); i++ ){

        }

    }

}
