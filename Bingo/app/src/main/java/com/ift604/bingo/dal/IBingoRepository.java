package com.ift604.bingo.dal;

import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Lobby;

import java.util.ArrayList;
import java.util.List;


public interface IBingoRepository {
  public Card getPlayerCard();
  public void sendWinnerCardForValidation();

  ArrayList<Lobby> getLobbiesNearMe();
}