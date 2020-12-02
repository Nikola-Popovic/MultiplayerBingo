package com.ift604.bingo.service;

import android.content.Intent;

import com.ift604.bingo.dal.RestServiceDatasource;

public class DrawnNumberService extends GenericRestService{
    public static final String NUMBER_DRAWN_ACTION = "NUMBER_DRAWN_ACTION" ;
    public static final String NEW_NUMBER_EXTRA = "NEW_NUMBER_EXTRA";

    public DrawnNumberService(String name) {
        super(name);
    }

    public DrawnNumberService() {
        super("drawnNumberService");
        bingoRepository = new RestServiceDatasource();
    }


    @Override
    protected Object restAction(Intent i) throws Exception {
        String previousNumber = bingoRepository.getPreviousNumber();
        return previousNumber;
    }

    @Override
    protected Intent onSuccess(Object o, Intent intentOutput) {
        intentOutput.putExtra(NEW_NUMBER_EXTRA, (String) o);
        return intentOutput;
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected Intent setAction(Intent intentOutput) {
        return null;
    }
}
