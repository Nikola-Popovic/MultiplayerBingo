package com.ift604.bingo.dal.dao;

import com.ift604.bingo.DummyCard;
import com.ift604.bingo.model.Card;

public class GameDAO extends GenericDataHandler {
    public GameDAO() {
        super();
    }

    public Card getPlayerCard() {
        return DummyCard.generateDummyCard();
    }
}
