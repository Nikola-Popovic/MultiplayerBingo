package com.ift604.bingo.dal.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.model.Participant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LobbyDatamapper {
    public Lobby buildLobby(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            Gson gson = new Gson();
            return (Lobby) gson.fromJson(jsonObject.toString(), new TypeToken<Lobby>(){}.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Participant mapUserToJson(String postDataToUrl) {
        try {
            JSONObject jsonObject = new JSONObject(postDataToUrl);
            Gson gson = new Gson();
            return (Participant) gson.fromJson(jsonObject.toString(), new TypeToken<Participant>(){}.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Lobby> buildLobbies(String result) {
        try {
            JSONArray arr = new JSONArray(result);
            Gson gson = new Gson();
            return (ArrayList<Lobby>) gson.fromJson(arr.toString(), new TypeToken<ArrayList<Lobby>>(){}.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
