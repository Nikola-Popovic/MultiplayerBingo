package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.util.Util;

import java.util.ArrayList;

public class FindLobbyNearMeService extends GenericRestService {
    public static String GET_LOBBY_BY_LOCATION_ACTION = "GET_LOBBY_BY_LOCATION_ACTION";
    public static String LOBBY_NEAR_ME_EXTRA = "LOBBY_NEAR_ME_EXTRA";

    public FindLobbyNearMeService(String name) {
        super(name);
    }

    public FindLobbyNearMeService() {
        super("findLobbyNearMeService");
        bingoRepository = new RestServiceDatasource();
    }

    @Override
    protected Object restAction(Intent i) throws Exception {
        ArrayList lobbies = bingoRepository.getLobbiesNearMe();
        return lobbies;
    }

    @Override
    protected Intent onSuccess(Object o, Intent intentOutput) {
        intentOutput.putExtra(LOBBY_NEAR_ME_EXTRA, (ArrayList) o);
        return intentOutput;
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected Intent setAction(Intent intentOutput) {
        intentOutput.setAction(GET_LOBBY_BY_LOCATION_ACTION);
        return intentOutput;
    }
}