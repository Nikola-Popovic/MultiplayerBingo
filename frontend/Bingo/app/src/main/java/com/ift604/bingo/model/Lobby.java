package com.ift604.bingo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Lobby implements Serializable {
    Integer id;
    @SerializedName("nom")
    String name;
    ArrayList<Participant> participants;
    String location;
    Participant host;
    //TODO GERER LA LOCATION


    public Lobby(Integer id, String name, ArrayList<Participant> participants, String location) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.participants = participants;
        this.location = location;
    }

    public Lobby() {

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

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Participant getHost() {
        return host;
    }

    public void setHost(Participant host) {
        this.host = host;
    }
}
