package com.example.chokl.multipletimerfinal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TimerRowsAdapter extends RecyclerView.Adapter<TimerRowsAdapter.ViewHolder>
{
    private Countdown [] mTimers;
    private int numTimers = 1; //increment when user creates new timer

    public TimerRowsAdapter()
    {
        mTimers = new Countdown[numTimers];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_item, viewGroup, false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        //not sure what to do here
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

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout mLinearLayout;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}