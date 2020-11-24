package com.ift604.bingo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ift604.bingo.model.Participant;
import com.ift604.bingo.util.Util;

import java.util.ArrayList;

public class WaitLobbyAdapter extends RecyclerView.Adapter<WaitLobbyAdapter.ViewHolder> {
    private ArrayList<Participant> participants = new ArrayList();

    public WaitLobbyAdapter(ArrayList<Participant> participants) {
        this.participants = participants;
    }


    @NonNull
    @Override
    public WaitLobbyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_wait_lobby_participant_item, parent, false);
        return new WaitLobbyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaitLobbyAdapter.ViewHolder holder, int position) {
        final Participant item = participants.get(position);
        populateData(holder, item);
    }

    private void populateData(@NonNull WaitLobbyAdapter.ViewHolder holder, Participant item) {
        holder.participantName.setText(Util.formatPlayerName(item));
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView participantName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            participantName = itemView.findViewById(R.id.wait_lobby_participant_name);
        }
    }
}
