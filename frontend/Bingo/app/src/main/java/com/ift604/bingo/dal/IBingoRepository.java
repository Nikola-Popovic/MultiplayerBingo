package com.ift604.bingo.dal;

import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.model.Participant;

import java.util.ArrayList;


public interface IBingoRepository {
  Card getPlayerCard();
  String getPreviousNumber();
  void sendWinnerCardForValidation();
  ArrayList<Lobby> getLobbiesNearMe(double longitude, double latitude) throws Exception;
  Lobby getLobby(int lobbyId) throws Exception;
  Lobby createLobby(int hostId, String name, double longitude, double latitude) throws Exception;
  void joinLobby(int lobbyId, int userId) throws Exception;
  void leaveLobby(int lobbyId, int userId) throws Exception;
  Participant createUser(String username) throws Exception;
  void startGame(int lobbyId, int hostId);
}