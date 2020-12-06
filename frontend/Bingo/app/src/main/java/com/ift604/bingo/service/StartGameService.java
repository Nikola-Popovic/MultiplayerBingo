package com.ift604.bingo.service;

import android.content.Intent;

public class StartGameService extends GenericRestService {
    public static final String START_GAME_ACTION = "START_GAME_ACTION";

    public static final String LOBBY_ID_PARAM ="LOBBY_ID_PARAM";
    public static final String PLAYER_ID ="LOBBY_HOST_PARAM";

    public StartGameService() {
        super("StartGameService");
    }

    @Override
    protected Object restAction(Intent i) throws Exception {
        final int lobbyId = i.getIntExtra(LOBBY_ID_PARAM, 0);
        final int lobbyHost = i.getIntExtra(PLAYER_ID, 0);
        bingoRepository.startGame(lobbyId, lobbyHost);
        return null;
    }

    @Override
    protected Intent onSuccess(Object o, Intent intentOutput) {
        return intentOutput;
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected Intent setAction(Intent intentOutput) {
        intentOutput.setAction(START_GAME_ACTION);
        return intentOutput;
    }

}
