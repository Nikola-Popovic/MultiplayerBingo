package com.ift604.bingo.dal.dao;

import android.util.Log;

import com.androidnetworking.common.ANResponse;
import com.ift604.bingo.exception.ResponseException;

import org.json.JSONException;
import org.json.JSONObject;

public class GameDAO extends GenericDataHandler {
    GameDatamapper gameDatamapper;
    private final String lobbyPath = "/lobby";
    private final String gamePath = "/game";

    public GameDAO() {
        super();
        gameDatamapper = new GameDatamapper();
    }

    public void startGame(int lobbyId, int hostId) throws Exception {
        String url = String.format("%s/%s/start", lobbyPath, String.valueOf(lobbyId));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostId", hostId);
        ANResponse response = postDataToUrl(url, jsonObject);

        if (!response.isSuccess()) {
            this.handleResponseError(response);
        }
    }

    public Boolean winGame(int cardId, int participantId, int lobbyId) throws Exception {
        String url = String.format("%s/%d/win", lobbyPath, lobbyId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("carteId", cardId);
        jsonObject.put("joueurId", participantId);
        ANResponse response = postDataToUrl(url, jsonObject);

        if (response.isSuccess()) {
            String message = String.format("Participant %s won.", participantId);
            Log.d("WIN_GAME_SUCCESS", message);
            return gameDatamapper.mapWinGameResult(response.getResult().toString());
        } else {
            this.handleResponseError(response);
            return false;
        }
    }
}
