package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.model.Participant;

public class CreateUserService extends IntentService {
    public static  String CREATE_USER_ACTION = "CREATE_USER_ACTION";
    public static  String CREATE_USER_EXTRA = "CREATE_USER_EXTRA";

    IBingoRepository bingoRepository;


    public CreateUserService(String name) {
        super(name);
    }

    public CreateUserService() {
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
        //TODO GET LE USERNAME
        Participant participant = bingoRepository.createUser("Nikolas Popovik");
        Intent i = new Intent();
        i.setAction(CREATE_USER_ACTION);
        i.putExtra(CREATE_USER_EXTRA, participant);
        sendBroadcast(i);
    }
}
