package com.ift604.bingo.dal.dao;

import android.util.Log;

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

    public ArrayList<Lobby> getLobbiesNearMe(double longitude, double latitude) throws Exception {
        String url = lobbyPath;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("longitude", longitude);
        jsonObject.put("latitude", latitude);
        ANResponse response = postDataToUrl(url, jsonObject);
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

    public Lobby createLobby(int hostId, String name, double longitude, double latitude) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hostId", hostId);
        jsonObject.put("nom", name);
        jsonObject.put("longitude", longitude);
        jsonObject.put("latitude", latitude);
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


    public void updateUser(int userId, String userName) throws Exception {
        Task<String> getTokenTask = FirebaseMessaging.getInstance().getToken();
        String token = Tasks.await(getTokenTask);
        String url = String.format("%s/%s", userPath, String.valueOf(userId));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", userName);
        jsonObject.put("token", token);
        ANResponse response = putDataToUrl(url, jsonObject);
        String msg = String.format("User%s : %s", userId, userName);
        if (response.isSuccess()) {
            Log.i("UPDATE_USER_SUCCESS", msg);
        }
        else {
            Log.e("UPDATE_USER_FAILED", msg, response.getError().fillInStackTrace());
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
