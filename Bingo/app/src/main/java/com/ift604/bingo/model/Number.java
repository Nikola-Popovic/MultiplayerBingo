package com.ift604.bingo.model;

public class Number {
    private int posX;
    private int posY;
    private String number;
    private boolean marked;

    public Number(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
