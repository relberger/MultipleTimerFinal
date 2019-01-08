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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private TimerRowsAdapter timerRowsAdapter;
    private ArrayList<Countdown> countdowns = new ArrayList<>() ;

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
                timerRowsAdapter.setNumTimers(1);
                timerRowsAdapter.notifyDataSetChanged();
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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        timerRowsAdapter = new TimerRowsAdapter(1);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(timerRowsAdapter);

        timerTimeSet(countdown);//call other countdown updating methods too
    }

    public void createNewCountdown(View view)
    {
        timerRowsAdapter.setNumTimers(timerRowsAdapter.getNumTimers() + 1);
        timerRowsAdapter.notifyDataSetChanged();
        Countdown countdown = new Countdown();
        countdowns.add(countdown);
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
