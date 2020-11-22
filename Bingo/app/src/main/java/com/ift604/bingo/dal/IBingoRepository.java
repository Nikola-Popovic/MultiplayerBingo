package com.ift604.bingo.dal;

import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.model.Participant;

import java.util.ArrayList;
import java.util.List;


public interface IBingoRepository {
  Card getPlayerCard();
  void sendWinnerCardForValidation();
  ArrayList<Lobby> getLobbiesNearMe();
  ArrayList<Participant> getLobbyParticipant(int lobbyId);
  Lobby createLobby(int hostId);
  void joinLobby(int lobbyId);
}