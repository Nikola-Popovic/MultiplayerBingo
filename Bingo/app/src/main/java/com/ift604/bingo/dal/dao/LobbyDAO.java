package com.ift604.bingo.dal.dao;

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
        String url = lobbyPath;
        try {
            String result = getDataFromUrl(url);
            return lobbyMapper.buildLobbies(result);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public Lobby getLobby(int lobbyId) {
        String url = String.format("%s/%s", lobbyPath, String.valueOf(lobbyId));
        try {
            String result = getDataFromUrl(url);
            return lobbyMapper.buildLobby(result);

        } catch (IOException e) {
            return new Lobby();
        }
    }

    public Lobby createLobby(int hostId, String name) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostId", hostId);
        jsonObject.put("nom", name);
        String jsonLobby = postDataToUrl(lobbyPath, jsonObject);
        return lobbyMapper.buildLobby(jsonLobby);
    }

    public void addPersonToLobby(int lobbyId, int userId) throws JSONException, IOException {
        String url = String.format("%s/%s/%s", lobbyPath, String.valueOf(lobbyId), userPath);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("joueurId", String.valueOf(userId));
        putDataToUrl(url, jsonObject);
    }

    public Participant createUser(String username) {

        String url = userPath;
        try {
            JSONObject jsonObject = new JSONObject();
            //TODO GET USERNAME
            jsonObject.put("username", username);
            String jsonReturnValue = postDataToUrl(url, jsonObject);
            return lobbyMapper.mapUserToJson(jsonReturnValue);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
