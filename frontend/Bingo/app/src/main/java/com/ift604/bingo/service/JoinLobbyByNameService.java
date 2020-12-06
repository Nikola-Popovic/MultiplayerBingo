package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;

public class JoinLobbyByNameService extends GenericRestService {
    public JoinLobbyByNameService(String name) {
        super(name);
    }

    @Override
    protected Object restAction(Intent i) throws Exception {
        return null;
    }

    @Override
    protected Intent onSuccess(Object o, Intent intentOutput) {
        return null;
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected Intent setAction(Intent intentOutput) {
        return null;
    }
}
