package com.ift604.bingo.dal;

import android.util.Log;

import com.ift604.bingo.dal.dao.TODODAT;
import com.ift604.bingo.model.Card;

public class Datasource implements IBingoRepository {
        TODODAT todorename = new TODODAT();
    @Override
    public Card getPlayerCard() {
        return todorename.buildCard("");
    }

    @Override
    public void sendWinnerCardForValidation() {

    }
}
