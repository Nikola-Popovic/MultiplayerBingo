package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;

import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.model.Lobby;

public class GetLobbyByAttributeService extends IntentService {
  public static final String GET_BY_ID_ACTION = "GET_LOBBY_BY_ID";
    public static final String LOBBY_ID_PARAM ="LOBBY_ID_PARAM";
    public static final String LOBBY_EXTRA ="LOBBY_EXTRA";

    IBingoRepository bingoRepository;

    public GetLobbyByAttributeService() {
        super("GetLobbyByAttributeService");
        bingoRepository = new RestServiceDatasource();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (GET_BY_ID_ACTION.equals(action)) {
                final int lobbyId = intent.getIntExtra(LOBBY_ID_PARAM, 0);
                Lobby lobby = bingoRepository.getLobby(lobbyId);
                Intent i = new Intent();
                i.setAction(GET_BY_ID_ACTION);
                i.putExtra(LOBBY_EXTRA, lobby);
                sendBroadcast(i);
            }
        }
    }

}
