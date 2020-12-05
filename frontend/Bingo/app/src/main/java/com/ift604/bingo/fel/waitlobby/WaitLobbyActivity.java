package com.ift604.bingo.fel.waitlobby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ift604.bingo.R;
import com.ift604.bingo.fel.game.GameActivity;
import com.ift604.bingo.fel.lobby.MainActivity;
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
        //TODO WILL SUBSCRIBE HERE. WHEN A NEW PERSON ENTER THE LOBBY, HIS NAME WILL BE THERE
        //TODO WILL SUBSCRIBE HERE. WHEN A NEW PERSON LEAVE THE LOBBY, HIS NAME WILL BE REMOVED
        //TODO WILL SUBSCRIBE HERE. WHEN THE HOST START THE GAME, THE GameActivity SHOULD START
    }

    private void leaveLobby() {
        Intent leaveLobbyService = new Intent(this, JoinLobbyService.class);
        leaveLobbyService.setAction(JoinLobbyService.LEAVE_LOBBY_ACTION);
        leaveLobbyService.putExtra(JoinLobbyService.LOBBY_ID, lobby.getId());
        leaveLobbyService.putExtra(JoinLobbyService.USER_ID, Util.getConnectedUserId(this));
        registerLeaveLobbyReceiver();
        startService(leaveLobbyService);
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
        stopService(getLobbyByAttributeService);
        unregisterReceiver(getLobbyResponseReceiver);
        super.onDestroy();
    }

    public void goBackToMainActivty() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
    }

    public class GetLobbyResponseReceiver extends BroadcastReceiver {
        public GetLobbyResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            lobby = (Lobby) intent.getSerializableExtra(GetLobbyByAttributeService.LOBBY_EXTRA);
            if (lobby == null)
                return;
            waitLobbyParticipantListFragment = WaitLobbyParticipantListFragment.newInstance(lobby.getParticipants());
            getSupportFragmentManager().beginTransaction().add(waitLobbyListFrameLayout.getId(), waitLobbyParticipantListFragment, "un autre joli tag").commit();

            Button startGameButton = findViewById(R.id.wait_lobby_start_button);
            int hostId = lobby.getHost().getId();
            int userId = Util.getConnectedUserId(context);
            if (hostId != userId)
            {
                startGameButton.setEnabled(false);
                startGameButton.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            leaveLobby();
        }
        return super.onKeyDown(keyCode, event);
    }


    public class LeaveLobbyReceiver extends BroadcastReceiver {

        public LeaveLobbyReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            goBackToMainActivty();
        }
    }
}