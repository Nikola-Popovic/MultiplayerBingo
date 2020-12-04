package com.ift604.bingo.dal.dao;

import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.common.ANResponse;
import com.ift604.bingo.fel.DummyCard;
import com.ift604.bingo.model.Card;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GameDAO extends GenericDataHandler {
    GameDatamapper gameDatamapper;
    private final String lobbyPath = "/lobby";
    private final String gamePath = "/game";
    private final String userPath = gamePath + "/joueur";
    public GameDAO() {
        super();
    }

    public Card getPlayerCard() {
        return DummyCard.generateDummyCard();
    }

    public void startGame(int lobbyId, int hostId) {
        try {
            String url = String.format("%s/%s/start", lobbyPath, String.valueOf(lobbyId));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hostId", hostId);
            ANResponse response = postDataToUrl(url, jsonObject);

            if(response.isSuccess()) {
                // Great !
                String message = String.format("Game %s started.", lobbyId);
                Log.i("START_GAME_SUCCESS", message);
                // What to do
            } else {
                Log.e("START_GAME_FAILED", response.getError().getMessage(), response.getError().getCause());
            }
       } catch (JSONException j) {}
    }

    public String getPreviousNumber() {
        // TODO : Supp fellas. Goota push them balls
        return "B5";
    }
}
