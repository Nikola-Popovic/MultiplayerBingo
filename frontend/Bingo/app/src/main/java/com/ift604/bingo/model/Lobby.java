package com.ift604.bingo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Lobby implements Serializable {
    Integer id;
    @SerializedName("nom")
    String name;
    ArrayList<Participant> participants;
    double longitude;
    double latitude;
    Participant host;


    public Lobby(Integer id, String name, ArrayList<Participant> participants, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.participants = participants;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Participant getHost() {
        return host;
    }

    public void setHost(Participant host) {
        this.host = host;
    }
}
