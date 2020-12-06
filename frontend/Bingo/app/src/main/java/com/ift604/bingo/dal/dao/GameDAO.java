package com.ift604.bingo.dal.dao;

import android.util.Log;

import com.androidnetworking.common.ANResponse;

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

    public void startGame(int lobbyId, int hostId) {
        try {
            String url = String.format("%s/%s/start", lobbyPath, String.valueOf(lobbyId));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hostId", hostId);
            ANResponse response = postDataToUrl(url, jsonObject);

            if (response.isSuccess()) {
                // Great !
                String message = String.format("Game %s started.", lobbyId);
                Log.i("START_GAME_SUCCESS", message);
                // What to do
            } else {
                Log.e("START_GAME_FAILED", response.getError().getMessage(), response.getError().getCause());
            }
        } catch (JSONException j) {
        }
    }

    public Boolean winGame(int cardId, int participantId, int lobbyId) throws Exception {
        String url = String.format("%s/%d/win", lobbyPath, lobbyId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("carteId", cardId);
        jsonObject.put("joueurId", participantId);
        ANResponse response = postDataToUrl(url, jsonObject);

        if (response.isSuccess()) {
            return gameDatamapper.mapWinGameResult(response.getResult().toString());
        } else {
            throw new Exception("Exception");
        }

    }
}
