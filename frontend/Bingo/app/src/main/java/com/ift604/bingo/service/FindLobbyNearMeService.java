package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.model.Lobby;

import java.util.ArrayList;

public class FindLobbyNearMeService extends IntentService {
    public static String GET_LOBBY_BY_LOCATION_ACTION = "GET_LOBBY_BY_LOCATION_ACTION";
    public static String LOBBY_NEAR_ME_BUNDLE = "LOBBY_NEAR_ME_BUNDLE";
    public static String LOBBY_NEAR_ME_EXTRA = "LOBBY_NEAR_ME_EXTRA";
    IBingoRepository bingoRepository;

    public FindLobbyNearMeService(String name) {
        super(name);
    }

    public FindLobbyNearMeService() {
        super("");
        bingoRepository = new RestServiceDatasource();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        ArrayList<Lobby> lobbies = bingoRepository.getLobbiesNearMe();
        Bundle b = new Bundle();
        b.putSerializable(LOBBY_NEAR_ME_BUNDLE, lobbies);
        Intent i = new Intent();
        i.setAction(GET_LOBBY_BY_LOCATION_ACTION);
        i.putExtra(LOBBY_NEAR_ME_EXTRA, b);
        sendBroadcast(i);
    }
}