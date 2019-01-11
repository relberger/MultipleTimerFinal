package com.example.chokl.multipletimerfinal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class TimerRowsAdapter extends RecyclerView.Adapter<TimerRowsAdapter.ViewHolder>
{
    private ArrayList<Countdown> mTimers;

    public TimerRowsAdapter (ArrayList<Countdown> timers)
    {
        if (timers != null) {
            mTimers = timers;
        }
        else {
            throw new IllegalArgumentException ("Timers List must not be null");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i)
    {
        View itemLayoutView = LayoutInflater.from (viewGroup.getContext ()).inflate
                (R.layout.rv_item, viewGroup, false);

        return new ViewHolder (itemLayoutView);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder viewHolder, int position)
    {
        setTags (viewHolder, position);
        updateTimerViews (viewHolder, position);

        // Store the position in the CD
        mTimers.get (position).setPosition (position);
    }

    private void setTags (ViewHolder viewHolder, int position)
    {
        viewHolder.etTimerName.setTag (position);
        viewHolder.tvTimerTime.setTag (position);
        viewHolder.buttonStartStop.setTag (position);
        viewHolder.buttonReset.setTag (position);
    }

    private void updateTimerViews (ViewHolder viewHolder, int position)
    {
        Countdown currentTimer = mTimers.get (position);
        if (currentTimer != null) {
            viewHolder.etTimerName.setText (currentTimer.getLabel ());
            viewHolder.tvTimerTime.setText (currentTimer.getRemainingTimeString ());
            viewHolder.buttonStartStop.setChecked (currentTimer.isTimerRunning ());
        }
    }

    @Override
    public int getItemCount ()
    {
        return mTimers.size ();
    }

    public Countdown getCountdownAt (int position)
    {
        return mTimers.get (position);
    }

    public void overwriteTimersListWith (ArrayList<Countdown> mTimers)
    {
        this.mTimers = mTimers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //public LinearLayout mLinearLayout;
        EditText etTimerName;
        TextView tvTimerTime;
        ToggleButton buttonStartStop;
        Button buttonReset;


        public ViewHolder (@NonNull View itemView)
        {
            super (itemView);
            etTimerName = itemView.findViewById (R.id.editTextTimerName);
            tvTimerTime = itemView.findViewById (R.id.textViewTimerTime);
            buttonStartStop = itemView.findViewById (R.id.buttonStartStop);
            buttonReset = itemView.findViewById (R.id.buttonReset);

            updateLabelInArrayList ();
        }

        private void updateLabelInArrayList ()
        {
            etTimerName.addTextChangedListener (new TextWatcher ()
            {
                @Override
                public void beforeTextChanged (CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged (CharSequence s, int start, int before, int count)
                {
                }

                @Override public void afterTextChanged (Editable s)
                {
                    mTimers.get (getAdapterPosition ()).setLabel (s.toString ());
                }
            });
        }
    }
}