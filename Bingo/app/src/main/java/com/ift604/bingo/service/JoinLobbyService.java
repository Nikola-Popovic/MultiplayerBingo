package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.RestServiceDatasource;


public class JoinLobbyService extends IntentService {
    public static final String JOIN_LOBBY_ACTION = "JOIN_LOBBY_ACTION";
    public static final String LEAVE_LOBBY_ACTION = "LEAVE_LOBBY_ACTION";
    public static final String LOBBY_ID = "LOBBY_ID";
    public static final String USER_ID = "USER_ID";
    RestServiceDatasource bingoRepository;

    public JoinLobbyService() {
        super("JoinLobbyService");
        bingoRepository = new RestServiceDatasource();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int lobbyId = intent.getIntExtra(LOBBY_ID, 0);
        int userId = intent.getIntExtra(USER_ID, 0);
        Intent i = new Intent();
        switch (intent.getAction()) {
            case JOIN_LOBBY_ACTION:
                bingoRepository.joinLobby(lobbyId, userId);
                i.setAction(JOIN_LOBBY_ACTION);
                i.putExtra(LOBBY_ID, lobbyId);
                sendBroadcast(i);
                break;
            case LEAVE_LOBBY_ACTION:
                bingoRepository.leaveLobby(lobbyId, userId);
                i.setAction(LEAVE_LOBBY_ACTION);
                sendBroadcast(i);
                break;
        }
    }
}
