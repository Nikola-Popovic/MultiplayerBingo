package com.ift604.bingo.dal.dao;

import com.ift604.bingo.DummyCard;
import com.ift604.bingo.model.Card;

public class TODODAT {
    public Card buildCard(String json) {
        return DummyCard.generateDummyCard();
    }
}
