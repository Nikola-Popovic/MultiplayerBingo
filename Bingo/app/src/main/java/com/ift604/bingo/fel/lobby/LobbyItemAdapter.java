package com.ift604.bingo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ift604.bingo.model.Lobby;

import java.util.ArrayList;

public class LobbyItemAdapter extends RecyclerView.Adapter<LobbyItemAdapter.ViewHolder> {
    private ArrayList<Lobby> lobbies;

    public LobbyItemAdapter(ArrayList<Lobby> lobbies) {
        this.lobbies = lobbies;
    }

    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lobby_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Lobby item = lobbies.get(position);
        populateData(holder, item);
        setBackGround(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WaitLobbyActivity.class);
                intent.putExtra(WaitLobbyActivity.LOBBY_ID, item.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    private void setBackGround(@NonNull ViewHolder holder, int position) {
        if(position % 4 == 0 ){
            holder.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.white_btn));
        } else if (position % 4 == 1 || position % 4 == 3) {
            holder.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_peach));
        } else {
            holder.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_orangy));
        }
    }

    private void populateData(@NonNull ViewHolder holder, Lobby item) {
        holder.lobbyName.setText(item.getName());
        holder.playerAmount.setText(item.getParticipants().size() + " Participants");
    }

    @Override
    public int getItemCount() {
        return lobbies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView lobbyName;
        TextView playerAmount;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lobbyName = itemView.findViewById(R.id.lobby_item_name);
            playerAmount = itemView.findViewById(R.id.lobby_item_player_amount);
            layout = itemView.findViewById(R.id.lobby_item_layout);
        }
    }

    public void setLobbies(ArrayList<Lobby> matches) {
        this.lobbies = matches;
    }
}
