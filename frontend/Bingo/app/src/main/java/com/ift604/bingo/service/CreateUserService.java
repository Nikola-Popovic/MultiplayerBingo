package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.model.Participant;
import com.ift604.bingo.util.Util;

public class CreateUserService extends GenericRestService {
    public static  String CREATE_USER_ACTION = "CREATE_USER_ACTION";
    public static  String CREATE_USER_EXTRA = "CREATE_USER_EXTRA";

    public CreateUserService(String name) {
        super(name);
    }

    public CreateUserService() {
        super("createUserService");
        bingoRepository = new RestServiceDatasource();
    }

    @Override
    protected Object restAction(Intent i) throws Exception {
        return bingoRepository.createUser("Nikolas Popovik");
    }

    @Override
    protected Intent onSuccess(Object o, Intent intentOutput) {
        intentOutput.putExtra(CREATE_USER_EXTRA, (Participant) o);
        return intentOutput;
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected Intent setAction(Intent intentOutput) {
        intentOutput.setAction(CREATE_USER_ACTION);
        return intentOutput;
    }
}
