package com.ift604.bingo.dal;

import android.util.Log;

import com.ift604.bingo.dal.dao.TODODAT;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Lobby;

import java.util.ArrayList;
import java.util.List;

public class Datasource implements IBingoRepository {
        TODODAT todorename = new TODODAT();
    @Override
    public Card getPlayerCard() {
        return todorename.buildCard("");
    }

    @Override
    public void sendWinnerCardForValidation() {

    }

    @Override
    public ArrayList<Lobby> getLobbiesNearMe() {
        return todorename.buildLobbies("");
    }
}
