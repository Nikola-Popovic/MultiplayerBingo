package com.ift604.bingo.dal;

import com.ift604.bingo.model.Card;


public interface IBingoRepository {
  public Card getPlayerCard();
}