package com.example.chokl.multipletimerfinal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TimerRowsAdapter extends RecyclerView.Adapter<TimerRowsAdapter.ViewHolder>
{

    private Countdown [] mTimers;
    private int numTimers = 1; //increment when user creates new timer


    public TimerRowsAdapter(int numTimers)
    {
        this.numTimers = numTimers;
        mTimers = new Countdown[numTimers];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position)
    {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.rv_item, viewGroup, false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {
        updateTimer(viewHolder, position);
    }

    private void updateTimer(ViewHolder viewHolder, int position) {
        viewHolder.tv_timerLabel.setText(mTimers[position].getLabel());
        viewHolder.tv_timeString.setText(mTimers[position].getRemainingTimeString());
    }

    @Override
    public int getItemCount()
    {
        return numTimers;
    }

    public Countdown[] getmTimers()
    {
        return mTimers;
    }

    public void setmTimers(Countdown[] mTimers)
    {
        this.mTimers = mTimers;
    }

    public int getNumTimers()
    {
        return numTimers;
    }

    public void setNumTimers(int numTimers)
    {
        this.numTimers = numTimers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout mLinearLayout;
        private TextView tv_timerLabel, tv_timeString;
        private ToggleButton tb_startStop;
        private Button b_reset;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.linearLayout);
            tv_timerLabel = mLinearLayout.findViewById(R.id.timerLabel);
            tv_timeString = mLinearLayout.findViewById(R.id.timeString);
            tb_startStop = mLinearLayout.findViewById(R.id.startStopButton);
            b_reset = mLinearLayout.findViewById(R.id.resetButton);
        }

    }

}