//package com.ift604.bingo.controller;
//
//import com.ift604.bingo.model.Coordinate;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class GameController {
//    //TODO CETTE INFO DEVRA SRM ETRE GARDÉ SOMEWHERE ELSE
//    Integer markedBox[][];
//    Integer playerCard[][];
//    public void markBoxForPlayer(Integer x, Integer y) {
//
//    }
//
//    private boolean verifyIfBing() {
//        return verifyFourCorners() && verifyHorizontalLine() && verifyVerticalLine() && verifyDiagonalLine();
//    }
//
//    private boolean verifyFourCorners(Coordinate coordinate) {
//        final ArrayList<Coordinate> fourCornerCoordinate = new ArrayList();
//        Coordinate topLeftCorner = new Coordinate(0, 0);
//        Coordinate topRightCorner = new Coordinate(0, playerCard[0].length);
//        Coordinate bottomLeftCorner = new Coordinate(playerCard.length,0);
//        Coordinate bottomRightCorner = new Coordinate(playerCard.length,playerCard[0].length);
//        fourCornerCoordinate.add(topLeftCorner);
//        fourCornerCoordinate.add(topRightCorner);
//        fourCornerCoordinate.add(bottomLeftCorner);
//        fourCornerCoordinate.add(bottomRightCorner);
//
//        if (fourCornerCoordinate.contains(coordinate)) {
//
//        }
//
//    }
//
//    private boolean verifyHorizontalLine(Coordinate coordinate) {
//
//    }
//
//    private boolean verifyVerticalLine(Coordinate coordinate) {
//
//    }
//
//    private boolean verifyDiagonalLine(Coordinate coordinate) {
//
//    }
//}
