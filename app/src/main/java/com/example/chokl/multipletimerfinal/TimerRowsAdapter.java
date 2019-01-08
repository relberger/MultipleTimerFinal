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

import java.util.ArrayList;

public class TimerRowsAdapter extends RecyclerView.Adapter<TimerRowsAdapter.ViewHolder> {
    private static OIClickListener sOIClickListener = new OIClickListener() {
        @Override
        public void onLabelClick(int position, View view) {

        }

        @Override
        public void onTimeClick(int position, View view) {

        }

        @Override
        public void onToggleClick(int position, View view) {

        }

        @Override
        public void onResetClick(int position, View view) {

        }
    };

    private ArrayList<Countdown>mTimers;
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
        updateTimer(viewHolder, position);
    }

    private void updateTimer(ViewHolder viewHolder, int position) {

        Countdown currentTimer = mTimers.get(position);
        if(currentTimer != null){
        if(currentTimer.getLabel() != null){
            viewHolder.tv_timerLabel.setText(currentTimer.getLabel());
        }
        if(currentTimer.getRemainingTime() != 0){
            viewHolder.tv_timeString.setText(currentTimer.getRemainingTimeString());
        }}
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

            tv_timerLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sOIClickListener.onLabelClick(getAdapterPosition(), view);
                }
            });

            tv_timeString.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sOIClickListener.onTimeClick(getAdapterPosition(), view);
                }
            });

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
        void onLabelClick(int position, View view);
        void onTimeClick(int position, View view);
        void onToggleClick(int position, View view);
        void onResetClick(int position, View view);

    }
}