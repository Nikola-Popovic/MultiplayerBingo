package com.ift604.bingo.fel.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ift604.bingo.R;

import java.util.ArrayList;
import java.util.List;

public class PreviousNumberAdapter extends RecyclerView.Adapter<PreviousNumberAdapter.ViewHolder>{
    private List<String> previousNumbers = new ArrayList();

    public PreviousNumberAdapter(List<String> previousNumbers) {
        this.previousNumbers = previousNumbers;
    }

    public void setPreviousNumbers(List<String> previousNumbers) {
        this.previousNumbers = previousNumbers;
    }

    @NonNull
    @Override
    public PreviousNumberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_wait_lobby_participant_item, parent, false);
        return new PreviousNumberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousNumberAdapter.ViewHolder holder, int position) {
        final String previousNumber = previousNumbers.get(position);
        populateData(holder, previousNumber);
    }

    private void populateData(@NonNull PreviousNumberAdapter.ViewHolder holder, String previousNumber) {
        holder.number.setText(previousNumber);
    }

    @Override
    public int getItemCount() {
        return previousNumbers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.game_activity_previous_number);
        }
    }
}
