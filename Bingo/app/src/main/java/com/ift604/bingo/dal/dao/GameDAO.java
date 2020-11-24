package com.ift604.bingo.dal.dao;

import com.ift604.bingo.DummyCard;
import com.ift604.bingo.model.Card;

public class GameDAO extends GenericDataHandler {
    GameDatamapper gameDatamapper;
    private final String gamePath = "/game";
    private final String userPath = gamePath + "/joueur";
    public GameDAO() {
        super();
    }

    public Card getPlayerCard() {
        return DummyCard.generateDummyCard();
    }

    public void startGame(int lobbyId, int hostId) {
//        String url = lobbyPath;
//        try {
//            String result = postDataToUrl(url,);
//            return gameDatamapper.buildGame(result);
//        } catch (IOException e) {
//            return new ArrayList<>();
//        }
    }
}
