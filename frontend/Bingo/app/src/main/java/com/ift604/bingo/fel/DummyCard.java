package com.ift604.bingo.fel;

import com.ift604.bingo.model.Box;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;
import com.ift604.bingo.model.Lobby;
import com.ift604.bingo.model.Participant;

import java.util.ArrayList;
import java.util.HashMap;


//TODO this class will have no purpose. His purpose for now is because there is no backend
public class DummyCard {
    public static Card generateDummyCard() {
        HashMap<Coordinate, Box> bColList = new HashMap();
        bColList.put(new Coordinate(0, 0), new Box("1", false));
        bColList.put(new Coordinate(0, 1), new Box("2", false));
        bColList.put(new Coordinate(0, 2), new Box("3", false));
        bColList.put(new Coordinate(0, 3), new Box("4", false));
        bColList.put(new Coordinate(0, 4), new Box("5", false));


        HashMap<Coordinate, Box> iColList = new HashMap();
        iColList.put(new Coordinate(1, 0), new Box("16", false));
        iColList.put(new Coordinate(1, 1), new Box("17", false));
        iColList.put(new Coordinate(1, 2), new Box("18", false));
        iColList.put(new Coordinate(1, 3), new Box("19", false));
        iColList.put(new Coordinate(1, 4), new Box("20", false));

        HashMap<Coordinate, Box> nColList = new HashMap();
        nColList.put(new Coordinate(2, 0), new Box("31", false));
        nColList.put(new Coordinate(2, 1), new Box("32", false));
        nColList.put(new Coordinate(2, 2), new Box("Free", false));
        nColList.put(new Coordinate(2, 3), new Box("34", false));
        nColList.put(new Coordinate(2, 4), new Box("35", false));

        HashMap<Coordinate, Box> gColList = new HashMap();
        gColList.put(new Coordinate(3, 0), new Box("51", false));
        gColList.put(new Coordinate(3, 1), new Box("52", false));
        gColList.put(new Coordinate(3, 2), new Box("53", false));
        gColList.put(new Coordinate(3, 3), new Box("54", false));
        gColList.put(new Coordinate(3, 4), new Box("55", false));

        HashMap<Coordinate, Box> oColList = new HashMap();
        oColList.put(new Coordinate(4, 0), new Box("71", false));
        oColList.put(new Coordinate(4, 1), new Box("72", false));
        oColList.put(new Coordinate(4, 2), new Box("73", false));
        oColList.put(new Coordinate(4, 3), new Box("74", false));
        oColList.put(new Coordinate(4, 4), new Box("75", false));

        bColList.putAll(iColList);
        bColList.putAll(nColList);
        bColList.putAll(gColList);
        bColList.putAll(oColList);

        return new Card(bColList, 5, 5);
    }

    private static ArrayList<Participant> dummyParticipant(int lobbyId) {
        ArrayList<Participant> participants = new ArrayList();
        for (int i = 0; i < 5; i++) {
            participants.add(new Participant(lobbyId + i, "Luc" + i + lobbyId));
        }
        return participants;
    }

    public static ArrayList<Lobby> dummyLobby() {
        ArrayList<Lobby> lobbies = new ArrayList();
        for (int i = 0; i < 10; i++) {
            lobbies.add(new Lobby(i, "Lobby " + i, dummyParticipant(i), "Location vide"));
        }
        return lobbies;
    }
}
