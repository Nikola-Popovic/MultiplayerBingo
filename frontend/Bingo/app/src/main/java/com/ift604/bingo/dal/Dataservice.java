package com.ift604.bingo.dal;

public class Dataservice {
    private IBingoRepository datasource;

    public Dataservice() {
        datasource = new RestServiceDatasource();
    }

    public IBingoRepository getDatasource() {
        return datasource;
    }
}