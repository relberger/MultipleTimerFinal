package com.example.chokl.multipletimerfinal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity
{
    private TimerRowsAdapter timerRowsAdapter = new TimerRowsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();

        setupFAB();

        setupTimers();
    }

    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                createNewCountdown(view);
            }
        });
    }

    public void setupTimers()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(timerRowsAdapter);
    }

    public void createNewCountdown(View view)
    {
        Countdown countdown = new Countdown();
        updateCountdown(countdown);
    }

    private void updateCountdown(Countdown countdown)
    {
        timerNameSet(countdown);
        timerTimeSet(countdown);
        startStopTimer(countdown);
    }

    public void timerNameSet(Countdown countdown)
    {
        EditText timerName = findViewById(R.id.editText);
        countdown.setLabel(timerName.getText().toString());
        timerName.setFocusable(false);
        timerName.setFocusableInTouchMode(true);
    }

    private void timerTimeSet(Countdown countdown)
    {
    }

    public void startStopTimer(Countdown countdown)
    {
        ToggleButton startStopButton = findViewById(R.id.startStopButton);

        if (startStopButton.isChecked())
        {
            countdown.runTimer();
        }
        else if (!startStopButton.isChecked())
        {
            countdown.setRunning(false);
        }
    }
}
