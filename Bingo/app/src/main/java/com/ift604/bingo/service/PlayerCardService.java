package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.Datasource;
import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.model.Card;

public class PlayerCardService extends IntentService {
    IBingoRepository bingoRepository;

    public PlayerCardService() {
        super("playerCardService");
        bingoRepository = new Datasource();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Card card = bingoRepository.getPlayerCard();
        Bundle b = new Bundle();
        b.putSerializable("generatedCard", card);
        Intent i = new Intent();
        i.setAction("PLAYER_CARD_RECEIVER");
        //TODO set action, if needed
        i.putExtra("playerCard", b);
        sendBroadcast(i);
    }
}
