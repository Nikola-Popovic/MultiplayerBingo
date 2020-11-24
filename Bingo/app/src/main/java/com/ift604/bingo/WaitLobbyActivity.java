package com.ift604.bingo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.service.GetLobbyByAttributeService;
import com.ift604.bingo.service.StartGameService;

public class WaitLobbyActivity extends AppCompatActivity {
    public Lobby lobby;
    private WaitLobbyParticipantListFragment waitLobbyParticipantListFragment;
    private FrameLayout waitLobbyListFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_lobby);
        int lobbyId = getIntent().getIntExtra("LOBBY_ID", 0);
        startGetLobbyService(lobbyId);
        registerGetLobbyReceiver();

        waitLobbyListFrameLayout = findViewById(R.id.wait_lobby_participant_frame_layout);

        final Activity thisActivity = this;
        Button cancelButton = findViewById(R.id.wait_lobby_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisActivity.finish();
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
        GetLobbyResponseReceiver receiver = new GetLobbyResponseReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GetLobbyByAttributeService.GET_BY_ID_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    private void startGetLobbyService(int lobbyId) {
        Intent intent = new Intent(this, GetLobbyByAttributeService.class);
        intent.setAction(GetLobbyByAttributeService.GET_BY_ID_ACTION);
        intent.putExtra(GetLobbyByAttributeService.LOBBY_ID_PARAM, lobbyId);
        startService(intent);
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
}