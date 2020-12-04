package com.ift604.bingo.dal.dao;

import com.androidnetworking.common.ANResponse;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.model.Participant;

import org.json.JSONObject;

import java.util.ArrayList;

public class LobbyDAO extends GenericDataHandler {
    LobbyDatamapper lobbyMapper;
    private final String lobbyPath = "/lobby";
    private final String userPath = "/user";

    public LobbyDAO() {
        super();
        lobbyMapper = new LobbyDatamapper();
    }

    public ArrayList<Lobby> getLobbiesNearMe() throws Exception {
        String url = lobbyPath;
        ANResponse response = getDataFromUrl(url);
        if (response.isSuccess()) {
            return lobbyMapper.buildLobbies(response.getResult().toString());
        } else {
            throw new Exception("Error");
        }
    }

    public Lobby getLobby(int lobbyId) throws Exception {
        String url = String.format("%s/%s", lobbyPath, String.valueOf(lobbyId));
        ANResponse response = getDataFromUrl(url);
        if (response.isSuccess()) {
            return lobbyMapper.buildLobby(response.getResult().toString());
        } else {
            throw new Exception("Error");
        }
    }

    public Lobby createLobby(int hostId, String name) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostId", hostId);
        jsonObject.put("nom", name);
        ANResponse response = postDataToUrl(lobbyPath, jsonObject);
        if (response.isSuccess()) {
            return lobbyMapper.buildLobby(response.getResult().toString());
        } else {
            throw new Exception("Error");
        }
    }

    public void addPersonToLobby(int lobbyId, int userId) throws Exception {
        String url = String.format("%s/%s%s", lobbyPath, String.valueOf(lobbyId), userPath);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("joueurId", String.valueOf(userId));
        ANResponse response = putDataToUrl(url, jsonObject);
        if (response.isSuccess()) {

        } else {
            throw new Exception("Error");
        }
    }

    public void removePersonToLobby(int lobbyId, int userId) throws Exception {
        String url = String.format("%s/%s/%s", lobbyPath, String.valueOf(lobbyId), userPath);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("joueurId", String.valueOf(userId));
        ANResponse response = putDataToUrl(url, jsonObject);
        if (response.isSuccess()) {
        } else {
            throw new Exception("Error");
        }
    }

    public Participant createUser(String username) throws Exception {
        Task<String> getTokenTask = FirebaseMessaging.getInstance().getToken();
        String token = Tasks.await(getTokenTask);
        String url = userPath;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("token", token);
        ANResponse response = postDataToUrl(url, jsonObject);
        if (response.isSuccess()) {
            return lobbyMapper.mapUserToJson(response.getResult().toString());
        }
        else {
            throw new Exception("Error");
        }
    }

    public void leaveLobby(int lobbyId, int userId) throws Exception {
        String url = String.format("%s/%s%s", lobbyPath, String.valueOf(lobbyId), userPath);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("joueurId", String.valueOf(userId));
        ANResponse response = deleteDataToUrl(url, jsonObject);
        if (response.isSuccess()) {

        }
        else {
            throw new Exception("Error");
        }
    }
}
