package com.ift604.bingo.controller;

import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Coordinate;

public class GameController {
    Card playerCard;

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
        return playerCard.isChecked(0,0) && playerCard.isChecked(0,4) && playerCard.isChecked(4,0) && playerCard.isChecked(4,4);
    }

    private boolean verifyHorizontalLine(Coordinate coordinate) {
        boolean bingo = true;
        int y = coordinate.getPosY();
        for (int row = 0; row < playerCard.getMaxX(); row++) {
            bingo &= playerCard.isChecked(row, y);
        }
        return bingo;
    }

    private boolean verifyVerticalLine(Coordinate coordinate) {
        boolean bingo = true;
        int x = coordinate.getPosX();
        for (int column = 0; column < playerCard.getMaxY(); column++) {
            bingo &= playerCard.isChecked(x, column);
        }
        return bingo;
    }

    private boolean verifyDiagonalLine() {
        boolean firstDiago = true;
        boolean secondDiago = true;
        for (int i = 0; i < 5; i++) {
            firstDiago &= playerCard.isChecked(i, i);
            secondDiago &= playerCard.isChecked(4 - i, i);
        }
        return firstDiago || secondDiago;
    }

    public Card getPlayerCard() {
        return playerCard;
    }
}
