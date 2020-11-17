package com.ift604.bingo;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.Datasource;
import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.model.Card;

//TODO MOVE DANS SERVICE
public class PlayerCardService extends IntentService {
    IBingoRepository bingoRepository;

    public PlayerCardService(String name) {
        super(name);
    }

    public PlayerCardService() {
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
        Card card = bingoRepository.getPlayerCard();
        Bundle b = new Bundle();
        b.putSerializable("generatedCard", card);
        Intent i = new Intent();
        i.setAction("RECEIVE");
        //TODO set action, if needed
        i.putExtra("playerCard", b);
        sendBroadcast(i);
    }
}

