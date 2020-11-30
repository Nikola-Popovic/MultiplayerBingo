package com.ift604.bingo.dal;

import com.ift604.bingo.dal.dao.GameDAO;
import com.ift604.bingo.dal.dao.LobbyDAO;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.model.Participant;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class RestServiceDatasource implements IBingoRepository {
    GameDAO gameDAO;
    LobbyDAO lobbyDAO;

    public RestServiceDatasource() {
        gameDAO = new GameDAO();
        lobbyDAO = new LobbyDAO();
    }

    @Override
    public Card getPlayerCard() {
        return gameDAO.getPlayerCard();
    }

    @Override
    public void sendWinnerCardForValidation() {

    }

    @Override
    public ArrayList<Lobby> getLobbiesNearMe(double longitude, double latitude) {
        return lobbyDAO.getLobbiesNearMe(longitude, latitude);
    }

    @Override
    public Lobby getLobby(int lobbyId) {
        return lobbyDAO.getLobby(lobbyId);
    }

    @Override
    public Lobby createLobby(int hostId, String name, double longitude, double latitude) {
        try {
            return lobbyDAO.createLobby(hostId, name, longitude, latitude);
        } catch (Exception e) {
            return new Lobby();
        }
    }

    @Override
    public void joinLobby(int lobbyId, int userId) {
        try {
            lobbyDAO.addPersonToLobby(lobbyId, userId);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void leaveLobby(int lobbyId, int userId) {
        try {
            lobbyDAO.leaveLobby(lobbyId, userId);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Participant createUser(String username) {
        return lobbyDAO.createUser(username);
    }

    @Override
    public void startGame(int lobbyId, int hostId) {
        try {
            gameDAO.startGame(lobbyId, hostId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
