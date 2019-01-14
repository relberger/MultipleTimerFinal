package com.example.chokl.multipletimerfinal;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private TimerRowsAdapter mTimerRowsAdapter;
    private ArrayList<Countdown> mCountdowns;
    private View mSnackBarContainer;
    private Thread mThread;
    private boolean mPlaySound;


    @Override protected void onDestroy ()
    {
        // Remove all CD Handler callbacks and also interrupt/stop our Activity's "master" timer
        stopAllRunningTimers ();
        mThread.interrupt ();
        super.onDestroy ();
    }

    private void stopAllRunningTimers ()
    {
        for (Countdown countdown : mCountdowns) {
            if (countdown.isTimerRunning ()) {
                countdown.setTimerRunning (false);
            }
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState)
    {
        super.onSaveInstanceState (outState);
        Gson gson = new Gson ();
        Type cdType = new TypeToken<ArrayList<Countdown>> ()
        {
        }.getType ();
        String serialized = gson.toJson (mCountdowns, cdType);
        outState.putString ("CDs", serialized);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        setupToolbar ();
        setupFAB ();
        mSnackBarContainer = findViewById (R.id.activityMain);

        initCountdowns (savedInstanceState);
        setupTimers ();

        setupActivityTimerThread ();
        mThread.start ();
    }

    private void setupActivityTimerThread ()
    {
        mThread = new Thread ()
        {
            @Override
            public void run ()
            {
                try {
                    while (!mThread.isInterrupted ()) {
                        Thread.sleep (1000);
                        runOnUiThread (() -> {
                            for (Countdown countdown : mCountdowns) {
                                if (countdown.isTimerRunning ()) {
                                    mTimerRowsAdapter.notifyItemChanged (countdown.getPosition ());
                                    checkTimeUp (countdown);
                                }
                            }
                        });
                    }
                }
                catch (InterruptedException ignored) {
                }
            }
        };
    }

    private void checkTimeUp (Countdown countdown)
    {
        if (countdown.isTimerRunning () &&
                countdown.getRemainingTimeString ().equals ("00:00:00")) {
            Uri notification = RingtoneManager.getDefaultUri (RingtoneManager.TYPE_ALARM);
            MediaPlayer mp = MediaPlayer.create (getApplicationContext (), notification);
            if (mPlaySound) {
                mp.start ();
            }

            AlertDialog.Builder timeUp = new AlertDialog.Builder (this);

            String title = !countdown.getLabel ().isEmpty () ?
                           countdown.getLabel () :
                           "Timer " + (countdown.getPosition () + 1);

            timeUp.setTitle (title);
            timeUp.setMessage (title + " is done.");

            timeUp.setPositiveButton ("OK", (dialog, which) -> {
                if (mPlaySound) {
                    mp.stop ();
                }
                resetTimer (countdown, countdown.getPosition ());
            });
            timeUp.show ();
        }
    }

    private void initCountdowns (Bundle savedInstanceState)
    {
        if (savedInstanceState == null) {
            mCountdowns = new ArrayList<> ();
            createAndAddCountdownObject ();
        }
        else {
            mCountdowns = getSavedListFromBundle (savedInstanceState);
            // Although running/paused state of each CD is saved in the bundle, onDestroy stops all
            restartTimerHandlersInActiveTimers ();
        }
    }

    private ArrayList<Countdown> getSavedListFromBundle (Bundle savedInstanceState)
    {
        Gson gson = new Gson ();
        Type cdType = new TypeToken<ArrayList<Countdown>> ()
        {
        }.getType ();
        String serialized = savedInstanceState.getString ("CDs");
        return gson.fromJson (serialized, cdType);
    }

    private void restartTimerHandlersInActiveTimers ()
    {
        if (mCountdowns != null) {
            for (Countdown current : mCountdowns) {
                if (current.isTimerRunning ()) {
                    current.setTimerRunning (true);
                }
            }
        }
    }

    private void setupToolbar ()
    {
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
    }

    private void setupFAB ()
    {
        FloatingActionButton fab = findViewById (R.id.fab);
        fab.setOnClickListener (view -> createNewCountdown ());
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater ().inflate (R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId ()) {
            case R.id.deleteAllTimers:
                mCountdowns.clear ();
                createAndAddCountdownObject ();
                mTimerRowsAdapter.notifyDataSetChanged ();
                break;
            case R.id.about:
                Snackbar.make (mSnackBarContainer, R.string.aboutBodyText, Snackbar.LENGTH_LONG)
                        .show ();
                break;
            case R.id.menuCheck:
                toggleMenuItem (item);
                mPlaySound = !mPlaySound;
                break;
            default:
                return super.onOptionsItemSelected (item);
        }
        return super.onOptionsItemSelected (item);
    }

    private void toggleMenuItem (MenuItem item)
    {
        item.setChecked (!item.isChecked ());
    }

    private void createNewCountdown ()
    {
        createAndAddCountdownObject ();
        mTimerRowsAdapter.notifyItemChanged (mTimerRowsAdapter.getItemCount () - 1);
    }

    private void setupTimers ()
    {
        RecyclerView recyclerView = findViewById (R.id.recyclerView);
        recyclerView.setHasFixedSize (false);
        mTimerRowsAdapter = new TimerRowsAdapter (mCountdowns);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager (this);

        recyclerView.setLayoutManager (layoutManager);
        recyclerView.setAdapter (mTimerRowsAdapter);
    }

    private void createAndAddCountdownObject ()
    {
        Countdown countdown = new Countdown ();
        mCountdowns.add (countdown);
    }

    public void rvClick (View view)
    {
        int position = Integer.parseInt (view.getTag ().toString ());
        Countdown currentCountDown = mTimerRowsAdapter.getCountdownAt (position);

        // timer name (EditText) is handled in the adapter - no need to handle that here
        if (view.getClass ().equals (AppCompatTextView.class))//(view == mTimerTime)
        {
            inputTimerTimeLabel (currentCountDown, position);
        }
        else if (view.getClass ().equals (ToggleButton.class)) //(view == mStartStopButton)
        {
            startStopTimer (currentCountDown, position);
        }
        else if (view.getClass ().equals (AppCompatButton.class)) //(view == mStartStopButton)
        //else if (view.getId () == findViewById (R.id.buttonReset).getId ())  //(view == mResetButton)
        {
            resetTimer (currentCountDown, position);
        }

        mTimerRowsAdapter.notifyItemChanged (position);
    }

    public void startStopTimer (Countdown countdown, final int position)
    {
        if (countdown.getRemainingTime () > 0) {
            countdown.setTimerRunning (!countdown.isTimerRunning ());
        }
        else {
            Toast.makeText (getApplicationContext (), countdown.getLabel () + " Timer is at 0",
                            Toast.LENGTH_LONG).show ();
        }
        mTimerRowsAdapter.notifyItemChanged (position);
    }

    private void resetTimer (Countdown countdown, final int position)
    {
        Countdown countdownNew = new Countdown ();
        mCountdowns.set (countdown.getPosition (), countdownNew);

        mTimerRowsAdapter.notifyItemChanged (position);
    }

    public void inputTimerTimeLabel (Countdown cd, final int position)
    {
        final Countdown countdown = cd;

        AlertDialog.Builder inputTimeAlert = new AlertDialog.Builder (this);
        inputTimeAlert.setTitle ("Enter the time");

        final EditText timeHoursInput = new EditText (this);
        timeHoursInput.setInputType (InputType.TYPE_CLASS_NUMBER);
        final EditText timeMinutesInput = new EditText (this);
        timeMinutesInput.setInputType (InputType.TYPE_CLASS_NUMBER);
        final EditText timeSecondsInput = new EditText (this);
        timeSecondsInput.setInputType (InputType.TYPE_CLASS_NUMBER);
        final TextView colon1 = new TextView (this);
        colon1.setText (":");
        final TextView colon2 = new TextView (this);
        colon2.setText (":");

        final int maxLength = 2;
        timeHoursInput.setFilters (new InputFilter[] {new InputFilter.LengthFilter (maxLength)});
        timeMinutesInput.setFilters (new InputFilter[] {new InputFilter.LengthFilter (maxLength)});
        timeSecondsInput.setFilters (new InputFilter[] {new InputFilter.LengthFilter (maxLength)});
        timeHoursInput.setFilters (new InputFilter[] {new InputFilterMinMax ("0", "23")});
        timeMinutesInput.setFilters (new InputFilter[] {new InputFilterMinMax ("0", "59")});
        timeSecondsInput.setFilters (new InputFilter[] {new InputFilterMinMax ("0", "59")});

        LinearLayout layout = new LinearLayout (this);
        layout.setOrientation (LinearLayout.HORIZONTAL);
        layout.setHorizontalGravity (Gravity.CENTER_HORIZONTAL);
        layout.addView (timeHoursInput);
        layout.addView (colon1);
        layout.addView (timeMinutesInput);
        layout.addView (colon2);
        layout.addView (timeSecondsInput);

        inputTimeAlert.setView (layout);

        inputTimeAlert.setPositiveButton ("OK", (dialog, which) -> {
            String hours = "00";
            if (!timeHoursInput.getText ().toString ().isEmpty ()) {
                if (timeHoursInput.getText ().toString ().length () == 1) {
                    hours = "0" + timeHoursInput.getText ().toString ();
                }
                else {
                    hours = timeHoursInput.getText ().toString ();
                }
            }
            String minutes = "00";
            if (!timeMinutesInput.getText ().toString ().isEmpty ()) {
                if (timeMinutesInput.getText ().toString ().length () == 1) {
                    minutes = "0" + timeMinutesInput.getText ().toString ();
                }
                else {
                    minutes = timeMinutesInput.getText ().toString ();
                }
            }
            String seconds = "00";
            if (!timeSecondsInput.getText ().toString ().isEmpty ()) {
                if (timeSecondsInput.getText ().toString ().length () == 1) {
                    seconds = "0" + timeSecondsInput.getText ().toString ();
                }
                else {
                    seconds = timeSecondsInput.getText ().toString ();
                }
            }

            // first reset time to zero, then add each of the hours, minutes and seconds
            countdown.setRemainingTime (0);
            countdown.addHours (Long.parseLong (hours));
            countdown.addMinutes (Long.parseLong (minutes));
            countdown.addSeconds (Long.parseLong (seconds));

            // This update has to be here, not in the calling method
            // Otherwise, it runs before the dialog sends the new time here
            mTimerRowsAdapter.notifyItemChanged (position);
        });

        inputTimeAlert.show ();
    }

    public class InputFilterMinMax implements InputFilter
    {
        //input filter taken from https://acomputerengineer.wordpress.com/2015/12/16/limit-number-range-in-edittext-in-android-using-inputfilter/
        private final int min;
        private final int max;

        public InputFilterMinMax (int min, int max)
        {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax (String min, String max)
        {
            this.min = Integer.parseInt (min);
            this.max = Integer.parseInt (max);
        }

        private boolean isInRange (int a, int b, int c)
        {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }

        @Override
        public CharSequence filter (CharSequence source, int start, int end, Spanned dest,
                                    int dStart, int dEnd)
        {
            try {
                int input = Integer.parseInt (dest.toString () + source.toString ());
                if (isInRange (min, max, input)) {
                    return null;
                }
            }
            catch (NumberFormatException ignored) {
            }
            return "";
        }

    }
}