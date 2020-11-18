package com.ift604.bingo.controller;

import com.ift604.bingo.model.Box;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameController {
    //TODO CETTE INFO DEVRA SRM ETRE GARDÉ SOMEWHERE ELSE
    //TODO ON VEUT TU PERMETTRE DE UNMARK
    Card playerCard;
    List<String> numberCalled;

    public GameController(Card card) {
        this.playerCard = card;
    }

    public void markBoxForPlayer(Coordinate coordinate) {
        playerCard.getNumber().get(coordinate).setMarked(true);
    }

    public boolean verifyIfBingo(Coordinate coordinate) {
        return verifyFourCorners(coordinate) || verifyHorizontalLine(coordinate) || verifyVerticalLine(coordinate);
    }

    private boolean verifyFourCorners(Coordinate coordinate) {
        Coordinate topLeftCorner = new Coordinate(0, 0);
        Coordinate topRightCorner = new Coordinate(0, playerCard.getMaxX() - 1);
        Coordinate bottomLeftCorner = new Coordinate(playerCard.getMaxY() - 1, 0);
        Coordinate bottomRightCorner = new Coordinate(playerCard.getMaxY() - 1, playerCard.getMaxX() - 1);

        HashMap<Coordinate, Box> playerGame = playerCard.getNumber();
        Box topLeftBox = playerGame.get(topLeftCorner);
        Box topRightBox = playerGame.get(topRightCorner);
        Box bottomLeftBox = playerGame.get(bottomLeftCorner);
        Box bottomRightBox = playerGame.get(bottomRightCorner);
        return (fourCornerMarked(topLeftBox, topRightBox, bottomLeftBox, bottomRightBox) && fourCornerNumberGotCalled(topLeftBox, topRightBox, bottomLeftBox, bottomRightBox));
    }

    private boolean fourCornerNumberGotCalled(Box topLeftBox, Box topRightBox, Box bottomLeftBox, Box bottomRightBox) {
        return true;
        //TODO ENLEVER LE COMMENTAIRE QUAND LE BACKEND VA PASSER DES NUMEROS
//        return numberCalled.contains(topLeftBox.getNumber())
//                && numberCalled.contains(topRightBox.getNumber())
//                && numberCalled.contains(bottomLeftBox.getNumber())
//                && numberCalled.contains(bottomRightBox.getNumber());
    }

    private boolean fourCornerMarked(Box topLeftBox, Box topRightBox, Box bottomLeftBox, Box bottomRightBox) {
        return topLeftBox.isMarked() && topRightBox.isMarked() && bottomLeftBox.isMarked() && bottomRightBox.isMarked();
    }

    private boolean verifyHorizontalLine(Coordinate coordinate) {
        for (int row = 0; row < playerCard.getMaxY(); row++) {
            if (!playerCard.getNumber().get(new Coordinate(row, coordinate.getPosY())).isMarked()) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyVerticalLine(Coordinate coordinate) {
        for (int column = 0; column < playerCard.getMaxY(); column++) {
            if (!playerCard.getNumber().get(new Coordinate(coordinate.getPosX(), column)).isMarked()) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyDiagonalLine(Coordinate coordinate) {
        return true;
    }
}
