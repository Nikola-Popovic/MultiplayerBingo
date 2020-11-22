package com.ift604.bingo.model;

import java.io.Serializable;
import java.util.List;

public class Lobby implements Serializable {
    Integer id;
    String name;
    List<Participant> participants;
    String location;
    //TODO GERER LA LOCATION


    public Lobby(Integer id, String name, List<Participant> participants, String location) {
        this.id = id;
        this.name = name;
        this.participants = participants;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
