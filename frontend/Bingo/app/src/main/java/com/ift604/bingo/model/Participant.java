package com.ift604.bingo.model;

import java.io.Serializable;
import java.util.Objects;

public class Participant implements Serializable {
    Integer id;
    String username;

    public Participant(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return id == that.id.intValue();
    }

}
