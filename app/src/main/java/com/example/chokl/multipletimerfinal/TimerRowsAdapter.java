package com.example.chokl.multipletimerfinal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimerRowsAdapter extends RecyclerView.Adapter<TimerRowsAdapter.ViewHolder> {
    private static AdapterView.OnItemClickListener sOIClickListener;


    private Countdown[] mTimers;
    private int numTimers = 1; //increment when user creates new timer

    public TimerRowsAdapter() {
        mTimers = new Countdown[numTimers];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_item, viewGroup, false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        updateTimer(viewHolder, position);
    }

    private void updateTimer(ViewHolder viewHolder, int position) {
        viewHolder.timeRemaining.setText(mTimers[position].getRemainingTimeString());
    }

    @Override
    public int getItemCount() {
        return numTimers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mLinearLayout;
        public TextView timeRemaining;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.linearLayout);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
