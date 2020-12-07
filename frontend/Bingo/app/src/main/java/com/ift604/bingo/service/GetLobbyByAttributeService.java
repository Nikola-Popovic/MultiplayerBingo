package com.ift604.bingo.service;

import android.content.Intent;

import com.ift604.bingo.model.Lobby;

public class GetLobbyByAttributeService extends GenericRestService {
  public static final String GET_BY_ID_ACTION = "GET_LOBBY_BY_ID_ACTION";

    public static final String LOBBY_ID_PARAM ="LOBBY_ID_PARAM";
    public static final String LOBBY_EXTRA ="LOBBY_EXTRA";

    public GetLobbyByAttributeService() {
        super("getLobbyByAttributeService");
    }

    @Override
    protected Object restAction(Intent i) throws Exception {
        final int lobbyId = i.getIntExtra(LOBBY_ID_PARAM, 0);
        Lobby lobby = bingoRepository.getLobby(lobbyId);
        return lobby;
    }

    @Override
    protected Intent onSuccess(Object o, Intent intentOutput) {
        intentOutput.putExtra(LOBBY_EXTRA, (Lobby) o);
        return intentOutput;
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected Intent setAction(Intent intentOutput) {
        intentOutput.setAction(GET_BY_ID_ACTION);
        return intentOutput;
    }

}
