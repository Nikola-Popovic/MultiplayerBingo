package com.ift604.bingo.model;

import java.io.Serializable;
import java.util.HashMap;

public class Card implements Serializable {
    private int id;
    private int lobbyId;
    private HashMap<Coordinate, Box> number;
    private int maxX;
    private int maxY;

    public Card(int id, int lobbyId, HashMap<Coordinate, Box> numbers, int maxX, int maxY) {
        this.id = id;
        this.lobbyId = lobbyId;
        this.number = numbers;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public Card(HashMap<Coordinate, Box> number) {
        this.number = number;
    }

    public HashMap<Coordinate, Box> getNumber() {
        return number;
    }

    public void setNumber(HashMap<Coordinate, Box> number) {
        this.number = number;
    }

    public boolean isChecked(int x, int y)
    {
        return number.get(new Coordinate(x, y)).isMarked();
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(int lobbyId) {
        this.lobbyId = lobbyId;
    }
}
