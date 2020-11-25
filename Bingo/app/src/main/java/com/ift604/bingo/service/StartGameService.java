package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;

import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.dal.RestServiceDatasource;

public class StartGameService extends IntentService {
    public static final String START_GAME_SERVICE = "START_GAME_SERVICE";
    public static final String LOBBY_ID_PARAM ="LOBBY_ID_PARAM";
    public static final String LOBBY_EXTRA ="LOBBY_EXTRA";

    IBingoRepository bingoRepository;

    public StartGameService() {
        super("StartGameService");
        bingoRepository = new RestServiceDatasource();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
//            final String action = intent.getAction();
//            if (GET_BY_ID_ACTION.equals(action)) {
//                final int lobbyId = intent.getIntExtra(LOBBY_ID_PARAM, 0);
//                Lobby lobby = bingoRepository.getLobby(lobbyId);
                Intent i = new Intent();
                i.setAction(START_GAME_SERVICE);
                sendBroadcast(i);
//            }
        }
    }

}
