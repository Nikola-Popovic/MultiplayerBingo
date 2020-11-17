package com.ift604.bingo.model;

import java.io.Serializable;
import java.util.List;

public class Card implements Serializable {
    private List<Column> columns;

    public Card(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }
}
