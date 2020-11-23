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

    public ArrayList<Participant> getLobbyParticipants(int lobbyId) {
        return (ArrayList<Participant>) DummyCard.dummyLobby().get(0).getParticipants();
    }

    public Lobby createLobby(int hostId, String name) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostId", hostId);
      //  jsonObject.put("name", name);
        String jsonLobby = postDataToUrl(lobbyPath, jsonObject);
        return lobbyMapper.buildLobby(jsonLobby);
    }

    public void addPersonToLobby(int lobbyId, int userId) {
        //TODO DO PUT
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
