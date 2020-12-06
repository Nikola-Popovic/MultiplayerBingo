package com.ift604.bingo.model;

import java.io.Serializable;

public class Boule implements Serializable {
    private String number;
    private Integer lobbyId;

    public Boule(String number, Integer lobbyId) {
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
