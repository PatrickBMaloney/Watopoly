package com.example.watopoly.model;

import android.graphics.Canvas;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    private ArrayList<Tile> boardTiles = new ArrayList<>();
    private Canvas canvas;

    public void setBoardInfo(Pair<ArrayList<Tile>, Canvas> boardInfo) {
        boardTiles = boardInfo.first;
        canvas = boardInfo.second;
    }

    //TODO:
    public void move(Player player, int steps) {
        //  use canvas to draw player bitmap
    }
}
