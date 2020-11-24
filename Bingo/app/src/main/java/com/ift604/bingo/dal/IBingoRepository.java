package com.ift604.bingo.dal;

import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.model.Participant;

import java.util.ArrayList;


public interface IBingoRepository {
  Card getPlayerCard();
  void sendWinnerCardForValidation();
  ArrayList<Lobby> getLobbiesNearMe();
  Lobby getLobby(int lobbyId);
  Lobby createLobby(int hostId, String name);
  void joinLobby(int lobbyId, int userId);
  void leaveLobby(int lobbyId, int userId);
  Participant createUser(String username);
  void startGame(int lobbyId, int hostId);
}