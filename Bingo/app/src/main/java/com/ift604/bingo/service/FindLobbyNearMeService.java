package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.Datasource;
import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.model.Lobby;

import java.util.ArrayList;

public class FindLobbyNearMeService extends IntentService {
    IBingoRepository bingoRepository;

    public FindLobbyNearMeService(String name) {
        super(name);
    }

    public FindLobbyNearMeService() {
        super("");
        bingoRepository = new Datasource();
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
        b.putSerializable("lobbiesNearMeBundle", lobbies);
        Intent i = new Intent();
        //TODO MAKE THIS IN A VAR
        i.setAction("LOBBIES");
        //TODO set action, if needed
        i.putExtra("lobbiesNearMe", b);
        sendBroadcast(i);
    }
}