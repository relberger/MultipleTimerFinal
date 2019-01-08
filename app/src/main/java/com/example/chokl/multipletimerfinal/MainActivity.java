package com.example.chokl.multipletimerfinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private TimerRowsAdapter timerRowsAdapter;
    private ArrayList<Countdown> countdowns = new ArrayList<>();

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


    private void toggleMenuItem(MenuItem item)
    {
        item.setChecked(!item.isChecked());
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
        TextView timerName = findViewById(R.id.label);
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

    public void inputLabel(View view)
    {
        final Countdown countdown = new Countdown();

        AlertDialog.Builder inputLabelAlert = new AlertDialog.Builder(this);
        inputLabelAlert.setTitle("Enter the timer name");

        final EditText labelInput = new EditText(this);
        labelInput.setInputType(InputType.TYPE_CLASS_TEXT);

        inputLabelAlert.setView(labelInput);

        inputLabelAlert.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                countdown.setLabel(labelInput.getText().toString());
                TextView label = findViewById(R.id.label);
                label.setText(labelInput.getText().toString());
            }
        });

        inputLabelAlert.show();
    }

    public void inputTime(View view)
    {
        final Countdown countdown = new Countdown();

        AlertDialog.Builder inputTimeAlert = new AlertDialog.Builder(this);
        inputTimeAlert.setTitle("Enter the time");

        final EditText timeHoursInput = new EditText(this);
        timeHoursInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        final EditText timeMinutesInput = new EditText(this);
        timeMinutesInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        final EditText timeSecondsInput = new EditText(this);
        timeSecondsInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        final TextView colon1 = new TextView(this);
        colon1.setText(":");
        final TextView colon2 = new TextView(this);
        colon2.setText(":");

        final int maxLength = 2;
        timeHoursInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        timeMinutesInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        timeSecondsInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        timeHoursInput.setFilters(new InputFilter[]{new InputFilterMinMax("0", "23")});
        timeMinutesInput.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        timeSecondsInput.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(timeHoursInput);
        layout.addView(colon1);
        layout.addView(timeMinutesInput);
        layout.addView(colon2);
        layout.addView(timeSecondsInput);

        inputTimeAlert.setView(layout);

        inputTimeAlert.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String hours = "00";
                if (!timeHoursInput.getText().toString().isEmpty())
                {
                    if (timeHoursInput.getText().toString().length() == 1)
                    {
                        hours = "0" + timeHoursInput.getText().toString();
                    }
                    else
                    {
                        hours = timeHoursInput.getText().toString();
                    }
                }

                String minutes = "00";
                if (!timeMinutesInput.getText().toString().isEmpty())
                {
                    if (timeMinutesInput.getText().toString().length() == 1)
                    {
                        minutes = "0" + timeMinutesInput.getText().toString();
                    }
                    else
                    {
                        minutes = timeMinutesInput.getText().toString();
                    }
                }
                String seconds = "00";
                if (!timeSecondsInput.getText().toString().isEmpty())
                {
                    if (timeSecondsInput.getText().toString().length() == 1)
                    {
                        seconds = "0" + timeSecondsInput.getText().toString();
                    }
                    else
                    {
                        seconds = timeSecondsInput.getText().toString();
                    }
                }

                countdown.setRemainingTimeString(hours + minutes + seconds);
                TextView time = findViewById(R.id.time);
                time.setText(hours + ":" + minutes + ":" + seconds);
            }
        });

        inputTimeAlert.show();
    }

    public class InputFilterMinMax implements InputFilter
    {
        //input filter taken from https://acomputerengineer.wordpress.com/2015/12/16/limit-number-range-in-edittext-in-android-using-inputfilter/

        private int min, max;

        public InputFilterMinMax(int min, int max)
        {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max)
        {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        private boolean isInRange(int a, int b, int c)
        {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
        {
            try
            {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            }
            catch (NumberFormatException nfe)
            {
            }
            return "";
        }
    }
}
