package com.ift604.bingo.dal.dao;

import com.ift604.bingo.DummyCard;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.model.Participant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class LobbyDAO extends GenericDataHandler {
    LobbyDatamapper lobbyMapper;
    private final String lobbyPath = "/lobby";
    private final String userPath = lobbyPath + "/joueur";
    public LobbyDAO() {
        super();
        lobbyMapper = new LobbyDatamapper();
    }

    public ArrayList<Lobby> getLobbiesNearMe() {
        return DummyCard.dummyLobby();
    }

    public Lobby getLobby(int lobbyId) {
        String url = String.format("%s/%s", String.valueOf(lobbyId));
        return lobbyMapper.buildLobby(url);
    }

    public Lobby createLobby(int hostId, String name) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostId", hostId);
      //  jsonObject.put("name", name);
        String jsonLobby = postDataToUrl(lobbyPath, jsonObject);
        return lobbyMapper.buildLobby(jsonLobby);
    }

    public void addPersonToLobby(int lobbyId, int userId) throws JSONException, IOException {
        String url = String.format("%s/%s/%s", lobbyPath, String.valueOf(lobbyId), userPath);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("joueurId", String.valueOf(userId));
        putDataToUrl(url, jsonObject);
    }

    public Participant createUser() {
        String url = userPath;
        try {
            String jsonReturnValue = postDataToUrl(url, new JSONObject());
            return lobbyMapper.mapUserToJson(jsonReturnValue);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
