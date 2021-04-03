package com.example.watopoly.model;

import android.graphics.Canvas;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    private ArrayList<Tile> boardTiles = new ArrayList<>();
    private transient Canvas canvas;

    public void setBoardInfo(Pair<ArrayList<Tile>, Canvas> boardInfo) {
        boardTiles = boardInfo.first;
        canvas = boardInfo.second;
        setup();
    }

    public Tile move(Player player, int steps) {
        int newPosition = (player.getPosition() + steps) % boardTiles.size();
        if (player.getPosition() + steps > boardTiles.size()) {
            player.passedGo();
        }

        Tile tile = boardTiles.get(newPosition);
        player.setPosition(newPosition);
        tile.landOn(player);

        //handle multiple people
        //  use canvas to draw player bitmap
        return boardTiles.get(player.getPosition());
    }

    private void setup() {
        int jailPosition = 0;
        for (int x = 0; x < boardTiles.size(); x++) {
            if (boardTiles.get(x) instanceof Jail) {
                jailPosition = x;
                break;
            }
        }

        for (int x = 0; x < boardTiles.size(); x++) {
            if (boardTiles.get(x) instanceof GoToJail) {
                GoToJail goToJail = (GoToJail) boardTiles.get(x);
                goToJail.setJailLocation(jailPosition);
            }

            if (boardTiles.get(x) instanceof CardTile) {
                CardTile cardTile = (CardTile) boardTiles.get(x);
            }
        }

    }
}
