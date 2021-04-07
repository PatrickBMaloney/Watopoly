package com.example.watopoly.model;

import com.example.watopoly.util.BoardTiles;
import java.io.Serializable;
import java.util.ArrayList;


public class Board implements Serializable {
    private ArrayList<Tile> boardTiles;

    public Board() {
        boardTiles = BoardTiles.getTiles();
        setup();
    }

    public ArrayList<Tile> getBoardTiles() {
        return boardTiles;
    }

    public Tile move(Player player, int steps) {
        int newPosition = (player.getPosition() + steps) % boardTiles.size();
        if (player.getPosition() + steps > boardTiles.size()) {
            player.passedGo();
        }

        // calculate new Tile
        Tile newTile = boardTiles.get(newPosition);
        //Do we need this?
//        if (newTile.getCurrNumberOfPlayers() == newTile.getMaxNumberOfPlayers()) {
//            newPosition = newPosition + 1;
//            newTile = boardTiles.get(newPosition);
//        }
        player.setPosition(newPosition);
        newTile.landOn(player);
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
        }

    }
}
