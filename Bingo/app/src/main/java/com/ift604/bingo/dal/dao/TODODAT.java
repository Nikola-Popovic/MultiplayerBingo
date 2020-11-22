package com.ift604.bingo.dal.dao;

import com.ift604.bingo.DummyCard;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Lobby;

import java.util.ArrayList;
import java.util.List;

//TODO ca va mapper l'objet du backend avec le notre
public class TODODAT {
    public Card buildCard(String json) {
        return DummyCard.generateDummyCard();
    }

    public ArrayList<Lobby> buildLobbies(String s) {
        return DummyCard.dummyLobby();
    }
}
