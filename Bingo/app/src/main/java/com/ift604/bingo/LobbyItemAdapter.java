package com.ift604.bingo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.util.Util;

import java.util.ArrayList;

public class LobbyItemAdapter extends RecyclerView.Adapter<LobbyItemAdapter.ViewHolder> {
    private ArrayList<Lobby> lobbies;

    public LobbyItemAdapter(ArrayList<Lobby> matches) {
        this.lobbies = matches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lobby_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Lobby item = lobbies.get(position);
        populateData(holder, item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO INFLATE WITH WAIT LOBBY FRAGMENT
            }
        });
    }

    private void populateData(@NonNull ViewHolder holder, Lobby item) {
        holder.lobbyName.setText(item.getName());
        holder.playerAmount.setText(String.valueOf(item.getParticipants().size()) + " Participants");
    }

    @Override
    public int getItemCount() {
        return lobbies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView lobbyName;
        TextView playerAmount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lobbyName = itemView.findViewById(R.id.lobby_item_name);
            playerAmount = itemView.findViewById(R.id.lobby_item_player_amount);
        }
    }

    public void setLobbies(ArrayList<Lobby> matches) {
        this.lobbies = matches;
    }
}
