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
    public ArrayList<Lobby> getLobbiesNearMe() {
        return lobbyDAO.getLobbiesNearMe();
    }

    @Override
    public Lobby getLobby(int lobbyId) {
        return lobbyDAO.getLobby(lobbyId);
    }

    @Override
    public Lobby createLobby(int hostId, String name) {
        try {
            return lobbyDAO.createLobby(hostId, name);
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
    public Participant createUser() {
        return lobbyDAO.createUser();
    }
}
