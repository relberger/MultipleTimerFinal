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

public class TimerRowsAdapter extends RecyclerView.Adapter<TimerRowsAdapter.ViewHolder> {
    private static OIClickListener sOIClickListener;

    private Countdown[] mTimers;
    private int numTimers = 1; //increment when user creates new timer


    public TimerRowsAdapter(int numTimers) {
        this.numTimers = numTimers;
        mTimers = new Countdown[numTimers];
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
        updateTimer(viewHolder, position);
    }

    private void updateTimer(ViewHolder viewHolder, int position) {

        Countdown currentTimer = mTimers[position];
        if(!currentTimer.getLabel().isEmpty()){
            viewHolder.tv_timerLabel.setText(currentTimer.getLabel());
        }
        if(currentTimer.getRemainingTime() != 0){
            viewHolder.tv_timeString.setText(currentTimer.getRemainingTimeString());
        }
    }

    @Override
    public int getItemCount() {
        return numTimers;
    }

    public Countdown[] getmTimers() {
        return mTimers;
    }

    public void setmTimers(Countdown[] mTimers) {
        this.mTimers = mTimers;
    }

    public int getNumTimers() {
        return numTimers;
    }

    public void setNumTimers(int numTimers) {
        this.numTimers = numTimers;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout mLinearLayout;
        TextView tv_timerLabel, tv_timeString;
        ToggleButton tb_startStop;
        Button b_reset;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mLinearLayout = itemView.findViewById(R.id.linearLayout);
            tv_timerLabel = mLinearLayout.findViewById(R.id.timerLabel);
            tv_timeString = mLinearLayout.findViewById(R.id.timeString);
            tb_startStop = mLinearLayout.findViewById(R.id.startStopButton);
            b_reset = mLinearLayout.findViewById(R.id.resetButton);

            tb_startStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sOIClickListener.onToggleClick(getAdapterPosition(), view);
                }
            });

            b_reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sOIClickListener.onResetClick(getAdapterPosition(), view);
                }
            });

        }

        @Override
        public void onClick(View view) {
        }


    }

    @SuppressWarnings("UnusedParameters")
    public interface OIClickListener {
        void onToggleClick(int position, View v);
        void onResetClick(int position, View v);
    }
}