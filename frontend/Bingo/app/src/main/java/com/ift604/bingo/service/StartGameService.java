package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;

import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.dal.RestServiceDatasource;
//TODO START GAME SERVICE
public class StartGameService extends GenericRestService {
    public static final String START_GAME_SERVICE = "START_GAME_SERVICE";
    public static final String LOBBY_ID_PARAM ="LOBBY_ID_PARAM";
    public static final String LOBBY_EXTRA ="LOBBY_EXTRA";


    public StartGameService() {
        super("StartGameService");
    }

    @Override
    protected Object restAction(Intent i) throws Exception {
        return null;
    }

    @Override
    protected Intent onSuccess(Object o, Intent intentOutput) {
        return null;
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected Intent setAction(Intent intentOutput) {
        intentOutput.setAction(START_GAME_SERVICE);
        return intentOutput;
    }

}
