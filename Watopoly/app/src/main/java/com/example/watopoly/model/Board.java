package com.example.watopoly.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    private ArrayList<Tile> boardTiles = new ArrayList<>();

    public void setTiles(ArrayList<Tile> tiles) {
        boardTiles = tiles;
    }

    //TODO:
    public void move(Player player, int steps) {
    }
}
