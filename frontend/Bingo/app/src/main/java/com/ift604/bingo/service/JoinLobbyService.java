package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.util.Util;


public class JoinLobbyService extends IntentService {
    public static final String JOIN_LOBBY_BY_ID_ACTION = "JOIN_LOBBY_BY_ACTION";
    public static final String JOIN_LOBBY_BY_NAME_ACTION = "JOIN_LOBBY_BY_NAME_ACTION";
    public static final String LEAVE_LOBBY_ACTION = "LEAVE_LOBBY_ACTION";

    public static final String LOBBY_ID = "LOBBY_ID";
    public static final String USER_ID = "USER_ID";
    public static final String LOBBY_NAME = "LOBBY_NAME";
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
            case JOIN_LOBBY_BY_ID_ACTION:
                joinLobbyByIdAction(lobbyId, userId, i);
                break;
            case JOIN_LOBBY_BY_NAME_ACTION:
                String lobbyName = intent.getStringExtra(LOBBY_NAME);
                joinLobbyByNameAction(lobbyName, userId, i);
                break;
            case LEAVE_LOBBY_ACTION:
                try {
                    bingoRepository.leaveLobby(lobbyId, userId);
                    i.putExtra(Util.IS_SUCCESS, true);
                } catch (Exception e) {
                    i.putExtra(Util.IS_SUCCESS, false);
                }
                i.setAction(LEAVE_LOBBY_ACTION);
                sendBroadcast(i);
                break;
        }
    }

    private void joinLobbyByIdAction(int lobbyId, int userId, Intent i) {
        try {
            bingoRepository.joinLobby(lobbyId, userId);
            i.putExtra(LOBBY_ID, lobbyId);
            i.putExtra(Util.IS_SUCCESS, true);
        } catch (Exception e) {
            i.putExtra(Util.IS_SUCCESS, false);
        }
        i.setAction(JOIN_LOBBY_BY_ID_ACTION);
        sendBroadcast(i);
    }

    private void joinLobbyByNameAction(String name, int userId, Intent i) {
        try {
           int lobbyId = bingoRepository.joinLobby(name, userId);
            i.putExtra(LOBBY_ID, lobbyId);
            i.putExtra(Util.IS_SUCCESS, true);
        } catch (Exception e) {
            i.putExtra(Util.IS_SUCCESS, false);
        }
        i.setAction(JOIN_LOBBY_BY_NAME_ACTION);
        sendBroadcast(i);
    }
}
