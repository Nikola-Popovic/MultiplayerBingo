package com.ift604.bingo.dal.datamapper;

import com.ift604.bingo.dal.BingoServerDatasource;

public abstract class GenericDataHandler {
    protected BingoServerDatasource bingoServerDatasource;

    public GenericDataHandler(BingoServerDatasource bingoServerDatasource) {
        this.bingoServerDatasource = bingoServerDatasource;
    }
}
