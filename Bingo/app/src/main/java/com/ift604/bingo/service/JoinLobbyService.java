package com.ift604.bingo.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.ift604.bingo.dal.RestServiceDatasource;
import com.ift604.bingo.model.Lobby;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class JoinLobbyService extends IntentService {
    public static final String JOIN_LOBBY_ACTION = "JOIN_LOBBY_ACTION";
    public static final String JOIN_LOBBY_EXTRA = "JOIN_LOBBY_EXTRA";
    RestServiceDatasource bingoRepository;
    public JoinLobbyService() {
        super("JoinLobbyService");
        bingoRepository = new RestServiceDatasource();
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Lobby lobby = bingoRepository.createLobby(0, "lobbyName");
        Intent i = new Intent();
        i.setAction(JOIN_LOBBY_ACTION);
        i.putExtra(JOIN_LOBBY_EXTRA, lobby);
        sendBroadcast(i);
    }

}
