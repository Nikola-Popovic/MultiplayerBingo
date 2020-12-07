package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.IBingoRepository;
import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.util.Util;

public abstract class GenericRestService<T> extends IntentService {
    protected IBingoRepository bingoRepository;
    private String serviceName;

    public GenericRestService(String name) {
        super(name);
        serviceName = name;
        bingoRepository = new RestServiceDatasource();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intentInput) {
        Intent intentOutput = new Intent();
        try {
            T returnValue = restAction(intentInput);
            intentOutput = onSuccess(returnValue, intentOutput);
            intentOutput.putExtra(Util.IS_SUCCESS, true);

            String tag = String.format("%s_SUCCESS.", serviceName);

            Log.d(tag, "Service Success");
        }
        catch(Exception e) {
            String tag = String.format("%s_FAILED.", serviceName);
            onError(e);
            Log.e(tag, e.getMessage(), e.getCause());
            intentOutput.putExtra(Util.IS_SUCCESS, false);
        }
        intentOutput = setAction(intentOutput);
        sendBroadcast(intentOutput);
    }

    protected abstract T restAction(Intent i) throws Exception;
    protected abstract Intent onSuccess(T t, Intent intentOutput);
    protected abstract void onError(Exception e);
    protected abstract Intent setAction(Intent intentOutput);
}
