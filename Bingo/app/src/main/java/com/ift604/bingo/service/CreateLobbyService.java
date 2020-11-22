package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.Datasource;
import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.model.Lobby;

public class CreateLobbyService extends IntentService {
    public final static String CREATE_LOBBY_ACTION = "CREATED_LOBBY";
    public final static String CREATED_LOBBY_EXTRA = "CREATED_LOBBY_EXTRA";
    IBingoRepository bingoRepository;

    public CreateLobbyService(String name) {
        super(name);
    }

    public CreateLobbyService() {
        super("CreateLobbyService");
        bingoRepository = new Datasource();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Lobby lobby = bingoRepository.createLobby(0);
        Intent i = new Intent();
        i.setAction(CREATE_LOBBY_ACTION);
        i.putExtra(CREATED_LOBBY_EXTRA, lobby);
        sendBroadcast(i);
    }
}
