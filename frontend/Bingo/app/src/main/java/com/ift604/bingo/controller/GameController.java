package com.ift604.bingo.controller;

import com.ift604.bingo.model.Box;
import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;

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
        return verifyFourCorners(coordinate) || verifyHorizontalLine(coordinate) || verifyVerticalLine(coordinate) || verifyDiagonalLine();
    }

    private boolean verifyFourCorners(Coordinate coordinate) {
        if (!fourCornerNumberGotCalled())
            return false;
        return playerCard.isChecked(0,0) && playerCard.isChecked(0,4) && playerCard.isChecked(4,0) && playerCard.isChecked(4,4);
//        Coordinate topLeftCorner = new Coordinate(0, 0);
//        Coordinate topRightCorner = new Coordinate(0, playerCard.getMaxX() - 1);
//        Coordinate bottomLeftCorner = new Coordinate(playerCard.getMaxY() - 1, 0);
//        Coordinate bottomRightCorner = new Coordinate(playerCard.getMaxY() - 1, playerCard.getMaxX() - 1);
//
//        HashMap<Coordinate, Box> playerGame = playerCard.getNumber();
//        Box topLeftBox = playerGame.get(topLeftCorner);
//        Box topRightBox = playerGame.get(topRightCorner);
//        Box bottomLeftBox = playerGame.get(bottomLeftCorner);
//        Box bottomRightBox = playerGame.get(bottomRightCorner);
//        return (fourCornerMarked(topLeftBox, topRightBox, bottomLeftBox, bottomRightBox) && fourCornerNumberGotCalled(topLeftBox, topRightBox, bottomLeftBox, bottomRightBox));
    }

    private boolean fourCornerNumberGotCalled() {
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
        if (!lineGotCalled())
            return false;
        boolean bingo = true;
        int y = coordinate.getPosY();
        for (int row = 0; row < playerCard.getMaxX(); row++) {
            bingo &= playerCard.isChecked(row, y);
        }
        return bingo;
    }

    private boolean verifyVerticalLine(Coordinate coordinate) {
        if (!lineGotCalled())
            return false;
        boolean bingo = true;
        int x = coordinate.getPosX();
        for (int column = 0; column < playerCard.getMaxY(); column++) {
            bingo &= playerCard.isChecked(x, column);
        }
        return bingo;
    }

    private boolean lineGotCalled()
    {
        return true;
        //TODO ENLEVER LE COMMENTAIRE QUAND LE BACKEND VA PASSER DES NUMEROS
//        for (int i = 0; i < 5; i++)
//        {
//            boolean verticalLine = true;
//            boolean horizontalLine = true;
//            for (int j = 0; j < 5; j++)
//            {
//                verticalLine &= numberCalled.contains( playerCard.getNumber().get(new Coordinate(i, j)).getNumber());
//                horizontalLine &= numberCalled.contains( playerCard.getNumber().get(new Coordinate(j, i)).getNumber());
//            }
//            if (verticalLine || horizontalLine)
//                return true;
//        }
//        return false;
    }

    private boolean verifyDiagonalLine() {
        if (!diagonalGotCalled())
            return false;
        boolean firstDiago = true;
        boolean secondDiago = true;
        for (int i = 0; i < 5; i++) {
            firstDiago &= playerCard.isChecked(i, i);
            secondDiago &= playerCard.isChecked(4 - i, i);
        }
        return firstDiago || secondDiago;
    }

    private boolean diagonalGotCalled()
    {
        return true;
        //TODO ENLEVER LE COMMENTAIRE QUAND LE BACKEND VA PASSER DES NUMEROS
//        return numberCalled.contains(topLeftBox.getNumber())
//                && numberCalled.contains(topRightBox.getNumber())
//                && numberCalled.contains(bottomLeftBox.getNumber())
//                && numberCalled.contains(bottomRightBox.getNumber());
    }

    public Card getPlayerCard() {
        return playerCard;
    }
}
