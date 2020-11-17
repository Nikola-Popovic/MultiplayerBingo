package com.ift604.bingo.model;

import java.io.Serializable;
import java.util.List;

public class Column implements Serializable {
    private String letter;
    private List<Integer> number;

    public Column(String letter, List number) {
        this.letter = letter;
        this.number = number;
    }

    public String getLetter() {
        return letter;
    }

    public List<Integer> getNumber() {
        return number;
    }
}
