package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.model.Card;

//TODO THIS SERVICE IS USELESS. IT WILL BE REPLACED BY A PUSH NOTIFICATION
public class PlayerCardService extends IntentService {
    IBingoRepository bingoRepository;

    public PlayerCardService() {
        super("playerCardService");
        bingoRepository = new RestServiceDatasource();
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
        i.putExtra("playerCard", b);
        sendBroadcast(i);
    }
}
