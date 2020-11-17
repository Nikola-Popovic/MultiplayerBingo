package com.ift604.bingo;

import com.ift604.bingo.model.Card;
import com.ift604.bingo.model.Column;

import java.util.ArrayList;


public class DummyCard {
    public static Card generateDummyCard() {
        ArrayList<Integer> bColList = new ArrayList<Integer>();
        bColList.add(1);
        bColList.add(2);
        bColList.add(3);
        bColList.add(4);
        bColList.add(5);
        Column bCol = new Column("B", bColList);

        ArrayList<Integer> iColList = new ArrayList<Integer>();
       iColList.add(16);
       iColList.add(17);
       iColList.add(18);
       iColList.add(19);
       iColList.add(20);
        Column iCol = new Column("I", iColList);

        ArrayList<Integer> nColList = new ArrayList<Integer>();
       nColList.add(31);
       nColList.add(32);
       nColList.add(33);
       nColList.add(34);
       nColList.add(35);
        Column nCol = new Column("N", nColList);

        ArrayList<Integer> gColList = new ArrayList<Integer>();
        gColList.add(46);
        gColList.add(47);
        gColList.add(48);
        gColList.add(49);
        gColList.add(50);
        Column gCol = new Column("G", gColList);

        ArrayList<Integer> oColList = new ArrayList<Integer>();
        oColList.add(61);
        oColList.add(62);
        oColList.add(63);
        oColList.add(64);
        oColList.add(65);
        Column oCol = new Column("O", oColList);

        ArrayList<Column> card = new ArrayList<>();
        card.add(bCol);
        card.add(iCol);
        card.add(nCol);
        card.add(gCol);
        card.add(oCol);
        return new Card(card);
    }
}
