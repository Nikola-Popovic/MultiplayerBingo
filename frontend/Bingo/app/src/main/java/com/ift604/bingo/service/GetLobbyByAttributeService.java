package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;

import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.util.Util;

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
            Intent i = new Intent();
            final String action = intent.getAction();
            if (GET_BY_ID_ACTION.equals(action)) {
                final int lobbyId = intent.getIntExtra(LOBBY_ID_PARAM, 0);
                Lobby lobby = null;
                try {
                    lobby = bingoRepository.getLobby(lobbyId);
                    i.putExtra(LOBBY_EXTRA, lobby);
                    i.putExtra(Util.IS_SUCCESS, true);
                } catch (Exception e) {
                    i.putExtra(Util.IS_SUCCESS, false);
                }
                i.setAction(GET_BY_ID_ACTION);
                sendBroadcast(i);
            }
        }
    }

}
