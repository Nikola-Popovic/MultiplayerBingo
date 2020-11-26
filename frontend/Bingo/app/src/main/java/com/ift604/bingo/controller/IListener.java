package com.ift604.bingo.controller;

import com.ift604.bingo.model.Coordinate;

import java.io.Serializable;

public interface IListener extends Serializable {
    public void onBoxClick(Coordinate coordinate);
}
