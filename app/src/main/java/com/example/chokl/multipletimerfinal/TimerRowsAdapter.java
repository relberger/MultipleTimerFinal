package com.example.chokl.multipletimerfinal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.util.ArrayList;


public class TimerRowsAdapter extends RecyclerView.Adapter<TimerRowsAdapter.ViewHolder>
{
    private ArrayList<Countdown> mTimers;
    private int numTimers; //increment when user creates new timer


    public TimerRowsAdapter(int numTimers) {
        this.numTimers = numTimers;
        mTimers = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.rv_item, viewGroup, false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        setTags(viewHolder, position);
        updateTimer(viewHolder, position);
    }

    private void setTags(ViewHolder viewHolder, int position)
    {
        viewHolder.tv_timerLabel.setTag(position);
        viewHolder.tv_timeString.setTag(position);
        viewHolder.tb_startStop.setTag(position);
        viewHolder.b_reset.setTag(position);

        //do for all four then do one on click for everything - call that view's tag
    }


    private void updateTimer(ViewHolder viewHolder, int position)
    {
        Countdown currentTimer = mTimers.get(position);
        if (currentTimer != null) {
            if (currentTimer.getLabel() != null) {
                viewHolder.tv_timerLabel.setText(currentTimer.getLabel());
            }
            if (currentTimer.getRemainingTime() != 0) {
                viewHolder.tv_timeString.setText(currentTimer.getRemainingTimeString());
            }
        }
    }

    @Override
    public int getItemCount() {
        return numTimers;
    }

    public ArrayList<Countdown> getmTimers() {
        return mTimers;
    }

    public void setmTimers(ArrayList<Countdown> mTimers) {
        this.mTimers = mTimers;
    }

    public int getNumTimers() {
        return numTimers;
    }

    public void setNumTimers(int numTimers) {
        this.numTimers = numTimers;
    }
    
/**
     * Serializes the current object
     * so it can be stored in the Bundle during rotation
     *
     */
    public static String getJSONof(TimerRowsAdapter obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * Reverses the serialization of the object String
     * back to a Timers object
     *
     *
     */
    public static TimerRowsAdapter getTimersFromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, TimerRowsAdapter.class);
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
            tv_timerLabel = itemView.findViewById(R.id.timerLabel);
            tv_timeString = itemView.findViewById(R.id.timeString);
            tb_startStop = itemView.findViewById(R.id.startStopButton);
            b_reset = itemView.findViewById(R.id.resetButton);
        }
    }
}