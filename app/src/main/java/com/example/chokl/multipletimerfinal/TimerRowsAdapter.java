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

public class TimerRowsAdapter extends RecyclerView.Adapter<TimerRowsAdapter.ViewHolder>
{
    /*private static OIClickListener sOIClickListenerLabel;
    private static OIClickListener sOIClickListenerTime;
    private static OIClickListener sOIClickListenerToggle;
    private static OIClickListener sOIClickListenerReset;

    public void sOIClickListenerLabel(OIClickListener oiClickListener)
    {
        TimerRowsAdapter.sOIClickListenerLabel = oiClickListener;
    }

    public void sOIClickListenerTime(OIClickListener oiClickListener)
    {
        TimerRowsAdapter.sOIClickListenerTime = oiClickListener;
    }

    public void sOIClickListenerToggle(OIClickListener oiClickListener)
    {
        TimerRowsAdapter.sOIClickListenerToggle = oiClickListener;
    }

    public void sOIClickListenerReset(OIClickListener oiClickListener)
    {
        TimerRowsAdapter.sOIClickListenerReset = oiClickListener;
    }*/

    private ArrayList<Countdown> mTimers;
    private int numTimers; //increment when user creates new timer


    public TimerRowsAdapter(int numTimers)
    {
        this.numTimers = numTimers;
        mTimers = new ArrayList<>();
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
        setTags(viewHolder, position);
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
        if (currentTimer != null)
        {
            if (currentTimer.getLabel() != null)
            {
                viewHolder.tv_timerLabel.setText(currentTimer.getLabel());
            }
            if (currentTimer.getRemainingTime() != 0)
            {
                viewHolder.tv_timeString.setText(currentTimer.getRemainingTimeString());
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return numTimers;
    }

    public ArrayList<Countdown> getmTimers()
    {
        return mTimers;
    }

    public void setmTimers(ArrayList<Countdown> mTimers)
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
            tv_timerLabel = itemView.findViewById(R.id.timerLabel);
            tv_timeString = itemView.findViewById(R.id.timeString);
            tb_startStop = itemView.findViewById(R.id.startStopButton);
            b_reset = itemView.findViewById(R.id.resetButton);
        }
    }
        /*LinearLayout mLinearLayout;
        TextView tv_timerLabel, tv_timeString;
        ToggleButton tb_startStop;
        Button b_reset;

        public ViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            tv_timerLabel = itemLayoutView.findViewById(R.id.timerLabel);
            tv_timeString = itemLayoutView.findViewById(R.id.timeString);
            tb_startStop = itemLayoutView.findViewById(R.id.startStopButton);
            b_reset = itemLayoutView.findViewById(R.id.resetButton);

            tv_timerLabel.setOnClickListener(this);

            tv_timeString.setOnClickListener(this);

            tb_startStop.setOnClickListener(this);

            b_reset.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            sOIClickListenerLabel.onLabelClick(getLayoutPosition(), v);
            sOIClickListenerTime.onTimeClick(getLayoutPosition(), v);
            sOIClickListenerToggle.onToggleClick(getLayoutPosition(), v);
            sOIClickListenerReset.onResetClick(getLayoutPosition(), v);
        }
    }
    //loop through and find which click has focus
    //try tags
    //one on click like final

    @SuppressWarnings("UnusedParameters")
    public interface OIClickListener
    {
        void onLabelClick(int position, View view);

        void onTimeClick(int position, View view);

        void onToggleClick(int position, View view);

        void onResetClick(int position, View view);
    }*/
}