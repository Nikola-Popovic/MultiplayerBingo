package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.model.Lobby;

public class CreateLobbyService extends IntentService {
    public final static String CREATE_LOBBY_ACTION = "CREATED_LOBBY";
    public final static String CREATED_LOBBY_EXTRA = "CREATED_LOBBY_EXTRA";
    public static final String USER_ID = "USER_ID";
    public static final String LOBBY_NAME = "LOBBY_NAME";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";

    IBingoRepository bingoRepository;

    public CreateLobbyService(String name) {
        super(name);
    }

    public CreateLobbyService() {
        super("CreateLobbyService");
        bingoRepository = new RestServiceDatasource();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int userId = intent.getIntExtra(USER_ID, 0);
        String lobbyName = intent.getStringExtra(LOBBY_NAME);
        double longitude = intent.getDoubleExtra(LONGITUDE, 0);
        double latitude = intent.getDoubleExtra(LATITUDE, 0);
        Lobby lobby = bingoRepository.createLobby(userId, lobbyName, longitude, latitude);
        Intent i = new Intent();
        i.putExtra(CREATED_LOBBY_EXTRA, lobby);
        i.setAction(CREATE_LOBBY_ACTION);
        //TODO COULD MANAGE ERROR
        sendBroadcast(i);
    }
}
