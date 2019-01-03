package com.example.chokl.multipletimerfinal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

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
            {createNewCountdown(view);
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
        //Countdown countdown = new Countdown();
        timerNameSet(view);
    }

    public void timerNameSet(View view)
    {
        EditText timerName = findViewById(R.id.editText);
        //timerRowsAdapter.getmTimers().length;
    }
}
