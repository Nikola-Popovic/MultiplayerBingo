package com.ift604.bingo.service;

import android.content.Intent;
import android.content.SharedPreferences;

import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.model.Participant;

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
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String userName = settings.getString("Username", "Anonyme");
        return bingoRepository.createUser(userName);
    }

    @Override
    protected Intent onSuccess(Object o, Intent intentOutput) {
        //todo save part maybe in sharedprof
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("IS_CREATED", true);
        editor.commit();

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
