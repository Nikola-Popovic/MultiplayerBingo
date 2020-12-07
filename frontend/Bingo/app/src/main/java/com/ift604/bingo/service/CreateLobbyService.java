package com.ift604.bingo.service;

import android.content.Intent;

import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.util.Util;

public class CreateLobbyService extends GenericRestService {
    public final static String CREATE_LOBBY_ACTION = "CREATED_LOBBY";

    public final static String CREATED_LOBBY_EXTRA = "CREATED_LOBBY_EXTRA";
    public static final String USER_ID = "USER_ID";
    public static final String LOBBY_NAME = "LOBBY_NAME";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";

    public CreateLobbyService(String name) {
        super(name);
    }

    public CreateLobbyService() {
        super("CreateLobbyService");
    }

    @Override
    protected Object restAction(Intent intent) throws Exception {
        int userId = intent.getIntExtra(USER_ID, 0);
        String lobbyName = intent.getStringExtra(LOBBY_NAME);
        double longitude = intent.getDoubleExtra(LONGITUDE, 0);
        double latitude = intent.getDoubleExtra(LATITUDE, 0);
        return bingoRepository.createLobby(userId, lobbyName, longitude, latitude);
    }

    @Override
    protected Intent onSuccess(Object o, Intent intentOutput) {
        intentOutput.putExtra(CREATED_LOBBY_EXTRA, (Lobby) o);
        intentOutput.putExtra(Util.IS_SUCCESS, true);
        return intentOutput;
    }

    @Override
    protected void onError(Exception e) {
    }

    @Override
    protected Intent setAction(Intent intentOutput) {
        intentOutput.setAction(CREATE_LOBBY_ACTION);
        return intentOutput;
    }
}
