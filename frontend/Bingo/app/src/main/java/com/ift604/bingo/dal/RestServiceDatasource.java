package com.ift604.bingo.dal;

import com.ift604.bingo.dal.dao.GameDAO;
import com.ift604.bingo.dal.dao.LobbyDAO;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.model.Participant;

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
    public String getPreviousNumber() {
        return gameDAO.getPreviousNumber();
    }

    @Override
    public void sendWinnerCardForValidation() {

    }

    @Override
    public ArrayList<Lobby> getLobbiesNearMe(double longitude, double latitude) throws Exception{
        return lobbyDAO.getLobbiesNearMe(longitude, latitude);
    }

    @Override
    public Lobby getLobby(int lobbyId) throws Exception {
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
    public void joinLobby(int lobbyId, int userId) throws Exception {
        lobbyDAO.addPersonToLobby(lobbyId, userId);
    }

    @Override
    public void leaveLobby(int lobbyId, int userId) throws Exception {
        lobbyDAO.leaveLobby(lobbyId, userId);
    }

    @Override
    public Participant createUser(String username) throws Exception {
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
