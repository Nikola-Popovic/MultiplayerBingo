package com.ift604.bingo.fel.waitlobby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ift604.bingo.R;
import com.ift604.bingo.fel.game.GameActivity;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.service.GetLobbyByAttributeService;
import com.ift604.bingo.service.JoinLobbyService;
import com.ift604.bingo.service.StartGameService;
import com.ift604.bingo.util.Util;

public class WaitLobbyActivity extends AppCompatActivity {
    public static final String LOBBY_ID = "LOBBY_ID";

    public Lobby lobby;
    private WaitLobbyParticipantListFragment waitLobbyParticipantListFragment;
    private FrameLayout waitLobbyListFrameLayout;
    private GetLobbyResponseReceiver getLobbyResponseReceiver;
    private Intent getLobbyByAttributeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_lobby);
        int lobbyId = getIntent().getIntExtra(LOBBY_ID, 0);
        startGetLobbyService(lobbyId);
        registerGetLobbyReceiver();

        waitLobbyListFrameLayout = findViewById(R.id.wait_lobby_participant_frame_layout);

        Button cancelButton = findViewById(R.id.wait_lobby_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveLobby();
            }
        });
        Button startGameButton = findViewById(R.id.wait_lobby_start_button);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStartGameService();
                registerWaitingLobby();
            }
        });
    }

    private void leaveLobby() {
        Intent leaveLobbyService = new Intent(this, JoinLobbyService.class);
        leaveLobbyService.setAction(JoinLobbyService.LEAVE_LOBBY_ACTION);
        leaveLobbyService.putExtra(JoinLobbyService.LOBBY_ID, lobby.getId());
        leaveLobbyService.putExtra(JoinLobbyService.USER_ID, Util.getConnectedUserId(this));
        startService(leaveLobbyService);
        registerLeaveLobbyReceiver();
    }

    private void registerLeaveLobbyReceiver() {
        LeaveLobbyReceiver receiver = new LeaveLobbyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(JoinLobbyService.LEAVE_LOBBY_ACTION);
        registerReceiver(receiver, intentFilter);
    }


    private void registerWaitingLobby() {
        WaitLobbyReceiver receiver = new WaitLobbyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(StartGameService.START_GAME_SERVICE);
        registerReceiver(receiver, intentFilter);
    }

    private void startGameActivity() {
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }

    private void startStartGameService() {
        Intent intent = new Intent(this, StartGameService.class);
        startService(intent);
    }

    public class WaitLobbyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            startGameActivity();
        }
    }

    private void registerGetLobbyReceiver() {
        getLobbyResponseReceiver = new GetLobbyResponseReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GetLobbyByAttributeService.GET_BY_ID_ACTION);
        registerReceiver(getLobbyResponseReceiver, intentFilter);
    }

    private void startGetLobbyService(int lobbyId) {
        getLobbyByAttributeService = new Intent(this, GetLobbyByAttributeService.class);
        getLobbyByAttributeService.setAction(GetLobbyByAttributeService.GET_BY_ID_ACTION);
        getLobbyByAttributeService.putExtra(GetLobbyByAttributeService.LOBBY_ID_PARAM, lobbyId);
        startService(getLobbyByAttributeService);
    }

    @Override
    public void onDestroy() {
        leaveLobby();
        stopService(getLobbyByAttributeService);
        unregisterReceiver(getLobbyResponseReceiver);
        super.onDestroy();
    }

    public class GetLobbyResponseReceiver extends BroadcastReceiver {
        public GetLobbyResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            lobby = (Lobby) intent.getSerializableExtra(GetLobbyByAttributeService.LOBBY_EXTRA);
            //TODO, SI ERREUR, AFFICHER ERREUR ET DETRUIRE ACTIVITÉ
            waitLobbyParticipantListFragment = WaitLobbyParticipantListFragment.newInstance(lobby.getParticipants());
            getSupportFragmentManager().beginTransaction().add(waitLobbyListFrameLayout.getId(), waitLobbyParticipantListFragment, "un autre joli tag").commit();
        }
    }


    public class LeaveLobbyReceiver extends BroadcastReceiver {

        public LeaveLobbyReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }
}