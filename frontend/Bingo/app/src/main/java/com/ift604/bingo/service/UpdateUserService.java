package com.ift604.bingo.service;

import android.content.Intent;

import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.util.Util;

public class UpdateUserService extends GenericRestService {
    public static final String UPDATE_USER_ACTION = "UPDATE_USER_ACTION";

    public static final String UPDATE_USER_NAME_EXTRA = "UPDATE_USER_NAME_EXTRA";
    public static final String UPDATE_USER_ID_EXTRA = "UPDATE_USER_ID_EXTRA";

    public UpdateUserService() {
        super("UpdateUserService");
        bingoRepository = new RestServiceDatasource();
    }

    public UpdateUserService(String name) {
        super(name);
    }

    @Override
    protected Object restAction(Intent i) throws Exception {
        final String userName = i.getStringExtra(UPDATE_USER_NAME_EXTRA);
        final int userId = i.getIntExtra(UPDATE_USER_ID_EXTRA, -1);
        bingoRepository.updateUser(userId, userName);
        return null;
    }

    @Override
    protected Intent onSuccess(Object o, Intent intentOutput) {
        return intentOutput;
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected Intent setAction(Intent intentOutput) {
        intentOutput.setAction(UPDATE_USER_ACTION);
        return intentOutput;
    }
}
