package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.RestServiceDatasource;


public class JoinLobbyService extends IntentService {
    public static final String JOIN_LOBBY_ACTION = "JOIN_LOBBY_ACTION";
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
        int userId =  intent.getIntExtra(USER_ID, 0);
        bingoRepository.joinLobby(lobbyId, userId);
        Intent i = new Intent();
        i.setAction(JOIN_LOBBY_ACTION);
        sendBroadcast(i);
    }
}
