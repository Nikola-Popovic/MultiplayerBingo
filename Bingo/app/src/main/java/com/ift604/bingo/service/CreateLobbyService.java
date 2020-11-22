package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.Datasource;
import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Lobby;

public class CreateLobbyService extends IntentService {
    public final static String createLobbyAction = "CREATED_LOBBY";
    IBingoRepository bingoRepository;
    public CreateLobbyService(String name) {
        super(name);
    }


    public CreateLobbyService() {
        super("CreateLobbyService");
        bingoRepository = new Datasource();
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Lobby lobby = bingoRepository.createLobby(0);
        Intent i = new Intent();
        //TODO PUT IN VARRIABLE
        i.setAction(createLobbyAction);
        i.putExtra("CREATED_LOBBY", lobby);
        sendBroadcast(i);
    }
}
