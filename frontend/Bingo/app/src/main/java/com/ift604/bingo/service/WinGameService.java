package com.ift604.bingo.service;

import android.content.Intent;

import com.ift604.bingo.dal.RestServiceDatasource;

public class WinGameService extends GenericRestService {
    public static final String WIN_GAME_ACTION = "WIN_GAME_ACTION";

    public static final String CARD_ID_EXTRA = "CARD_ID_EXTRA";
    public static final String WINNER_ID_EXTRA = "WINNER_ID_EXTRA";
    public static final String LOBBY_ID_EXTRA = "LOBBY_ID_EXTRA";
    public static final String IS_VALID_EXTRA = "IS_VALID_EXTRA";

    public WinGameService() {
        super("WinGameService");
        bingoRepository = new RestServiceDatasource();
    }

    public WinGameService(String name) {
        super(name);
    }

    @Override
    protected Object restAction(Intent i) throws Exception {
        int cardId = i.getIntExtra(CARD_ID_EXTRA, 0);
        int lobbyId = i.getIntExtra(LOBBY_ID_EXTRA, 0);
        int participantId = i.getIntExtra(WINNER_ID_EXTRA, 0);
        return bingoRepository.winGame(cardId, participantId, lobbyId);
    }

    @Override
    protected Intent onSuccess(Object o, Intent intentOutput) {
        intentOutput.putExtra(IS_VALID_EXTRA, (Boolean) o);
        return intentOutput;
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected Intent setAction(Intent intentOutput) {
        intentOutput.setAction(WIN_GAME_ACTION);
        return intentOutput;
    }
}
