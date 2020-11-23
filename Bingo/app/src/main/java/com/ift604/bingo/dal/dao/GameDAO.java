package com.ift604.bingo.dal.datamapper;

import com.ift604.bingo.dal.BingoServerDatasource;

public class GameDAO extends GenericDataHandler {
    public GameDAO(BingoServerDatasource bingoServerDatasource) {
        super(bingoServerDatasource);
    }
}
