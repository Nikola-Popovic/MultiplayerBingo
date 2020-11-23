package com.ift604.bingo.model;

import java.io.Serializable;

public class Participant implements Serializable {
    Integer id;
    String userName;

    public Participant(Integer id, String username) {
        this.id = id;
        this.userName = username;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
