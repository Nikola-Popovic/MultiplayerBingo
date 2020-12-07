package com.ift604.bingo.model;

import java.io.Serializable;

public class Ball implements Serializable {
    private String number;
    private Integer lobbyId;

    public Ball(String number, Integer lobbyId) {
        this.number = number;
        this.lobbyId = lobbyId;
    }

    public String getNumber() {
        return number;
    }

    public Integer getLobbyId() {
        return lobbyId;
    }
}
