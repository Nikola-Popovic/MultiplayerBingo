package com.ift604.bingo.fel.lobby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ift604.bingo.R;
import com.ift604.bingo.fel.waitlobby.WaitLobbyActivity;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.service.JoinLobbyService;
import com.ift604.bingo.util.Util;

import java.util.ArrayList;

public class LobbyItemAdapter extends RecyclerView.Adapter<LobbyItemAdapter.ViewHolder> {
    private ArrayList<Lobby> lobbies;
    private JoinLobbyReceiver joinLobbyReceiver;
    private Intent joinLobbyService;
    private Context ctx;
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
                ctx = v.getContext();
                startJoinLobbyService(item.getId());
                registerJoinLobbyReceiver();
            }
        });
    }

    private void registerJoinLobbyReceiver() {
        joinLobbyReceiver = new JoinLobbyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(JoinLobbyService.JOIN_LOBBY_ACTION);
        ctx.registerReceiver(joinLobbyReceiver, intentFilter);
    }

    private void startWaitLobbyFragment(int lobbyId) {
        Intent lobbyIntent = new Intent(ctx, WaitLobbyActivity.class);
        lobbyIntent.putExtra(WaitLobbyActivity.LOBBY_ID, lobbyId);
        ctx.startActivity(lobbyIntent);
    }

    private void startJoinLobbyService(int lobbyId) {
        joinLobbyService = new Intent(ctx, JoinLobbyService.class);
        joinLobbyService.putExtra(JoinLobbyService.LOBBY_ID, lobbyId);
        joinLobbyService.putExtra(JoinLobbyService.USER_ID, Util.getConnectedUserId(ctx));
        joinLobbyService.setAction(JoinLobbyService.JOIN_LOBBY_ACTION);
        ctx.startService(joinLobbyService);
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
        holder.playerAmount.setText(item.getParticipants().size() + R.string.participants);
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

    public class JoinLobbyReceiver extends BroadcastReceiver {

        public JoinLobbyReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            int lobbyId = intent.getIntExtra(JoinLobbyService.LOBBY_ID, 0);
            startWaitLobbyFragment(lobbyId);
        }
    }
}
