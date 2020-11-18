package com.ift604.bingo.model;

import java.io.Serializable;

public class Box implements Serializable {
    private String number;
    private boolean marked;

    public Box(String number, boolean marked) {
        this.number = number;
        this.marked = marked;
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
}
